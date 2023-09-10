package tw.idv.louislee.toolbox.generator

import tw.idv.louislee.toolbox.exception.AppException

open class GeneratorException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class UnsupportedGeneratorException(message: String? = null, cause: Throwable? = null) :
    GeneratorException(message, cause)
