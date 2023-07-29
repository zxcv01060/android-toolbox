package tw.idv.louislee.toolbox.encryption

sealed interface EncodedResult {
    object Blank : EncodedResult

    class TextEncodedResult(val encodedText: String) : EncodedResult

    class JwtEncodedResult(val header: String, val payload: String) : EncodedResult
}