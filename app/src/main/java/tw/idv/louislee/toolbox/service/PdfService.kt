package tw.idv.louislee.toolbox.service

import com.tom_roush.pdfbox.multipdf.Splitter
import com.tom_roush.pdfbox.pdmodel.PDDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import tw.idv.louislee.toolbox.extensions.splitFilenameAndExtension
import tw.idv.louislee.toolbox.model.PdfSeparateEvent
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import kotlin.io.path.outputStream

interface PdfService {
    fun separate(
        filename: String,
        stream: InputStream,
        isZipped: Boolean,
        outputPath: Path
    ): Flow<PdfSeparateEvent>
}

class PdfServiceImpl @Inject constructor() : PdfService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun separate(
        filename: String,
        stream: InputStream,
        isZipped: Boolean,
        outputPath: Path
    ) = flow { emit(separate(stream)) }
        .flatMapLatest { documents ->
            val result = if (isZipped) {
                toZipFile(outputPath, filename, documents)
            } else {
                saveTo(outputPath, filename, documents)
            }

            return@flatMapLatest result
                .catch { e ->
                    documents.forEach { it.close() }

                    throw e
                }
                .onStart {
                    emit(PdfSeparateEvent(processedCount = 0, totalFileCount = documents.size))
                }
        }
        .flowOn(Dispatchers.IO)

    private suspend fun separate(stream: InputStream): List<PDDocument> =
        withContext(Dispatchers.IO) {
            val pdf = PDDocument.load(stream)
            val splitter = Splitter()

            return@withContext splitter.split(pdf)
        }

    private fun toZipFile(
        path: Path,
        filename: String,
        files: List<PDDocument>
    ) = channelFlow {
        withContext(Dispatchers.IO) {
            val (filenameWithoutExtension, extension) = filename.splitFilenameAndExtension()
            val currentTimestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))

            val zipFilePath = path.resolve("$filenameWithoutExtension-$currentTimestamp.zip")
            val output = ZipOutputStream(zipFilePath.outputStream())

            for ((i, file) in files.withIndex()) {
                val memory = file.saveToMemory()
                output.putNextEntry(ZipEntry("$filenameWithoutExtension-${i + 1}.$extension"))
                memory.writeTo(output)
                memory.close()
                output.closeEntry()

                send(PdfSeparateEvent(processedCount = i + 1, totalFileCount = files.size))
            }

            output.close()
        }
    }

    private suspend fun PDDocument.saveToMemory() = withContext(Dispatchers.IO) {
        use {
            val memory = ByteArrayOutputStream()
            save(memory)

            memory
        }
    }

    private fun saveTo(path: Path, filename: String, files: List<PDDocument>) =
        channelFlow {
            withContext(Dispatchers.IO) {
                val (filenameWithoutExtension, extension) = filename.splitFilenameAndExtension()
                val currentTimestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))

                for ((i, separatedFile) in files.withIndex()) {
                    val separatedFilename =
                        "$filenameWithoutExtension-${i + 1}-$currentTimestamp.$extension"
                    val filePath = path.resolve(separatedFilename)
                    val output = filePath.outputStream()
                    separatedFile.save(output)

                    send(PdfSeparateEvent(processedCount = i + 1, totalFileCount = files.size))
                }
            }
        }
}
