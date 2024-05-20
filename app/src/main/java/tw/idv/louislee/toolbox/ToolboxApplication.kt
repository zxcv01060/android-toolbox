package tw.idv.louislee.toolbox

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToolboxApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        PDFBoxResourceLoader.init(this);
    }
}