package tw.idv.louislee

/**
 * 專案套件版本清單
 */
object Version {
    /**
     * Android Compile SDK及 Target SDK版本
     */
    const val SDK = 34

    /**
     * 能安裝此App的Android最低版本
     */
    const val MIN_SDK = 28

    /**
     * Kotlin Compose Compiler Extension
     *
     * 版本：https://developer.android.com/jetpack/androidx/releases/compose-kotlin
     */
    const val KOTLIN_COMPOSE_COMPILER_EXTENSION = "1.5.13"

    /**
     * Android KTX版本
     *
     * 版本：https://developer.android.com/jetpack/androidx/releases/core
     */
    const val ANDROID_CORE = "1.13.1"

    /**
     * Android Jetpack Compose
     *
     * 版本：https://github.com/chrisbanes/compose-bom/releases
     */
    const val COMPOSE = "2024.05.00"

    /**
     * Android Jetpack Activity Compose版本
     *
     * 版本：https://mvnrepository.com/artifact/androidx.activity/activity-compose?repo=google
     */
    const val ACTIVITY_COMPOSE = "1.9.0"

    /**
     * Android Lifecycle版本
     *
     * 版本：https://developer.android.com/jetpack/androidx/releases/lifecycle
     */
    const val LIFECYCLE = "2.7.0"

    /**
     * 以Jetpack Compose實作的各種選擇器、Dialog，包含日期、時間、顏色等
     *
     * Group：com.maxkeppeler.sheets-compose-dialogs
     *
     * 版本：https://github.com/maxkeppeler/sheets-compose-dialogs/releases
     */
    const val SHEETS_COMPOSE_DIALOGS = "1.3.0"

    /**
     * Jetpack DI套件 - Hilt
     *
     * Group：com.google.dagger
     *
     * 版本：https://mvnrepository.com/artifact/com.google.dagger/hilt-android
     */
    const val DAGGER_HILT = "2.51.1"

    /**
     * Hilt的Compose支援
     *
     * 版本：https://developer.android.com/jetpack/androidx/releases/hilt
     */
    const val DAGGER_HILT_COMPOSE = "1.2.0"

    /**
     * Android Jetpack Navigation
     *
     * Group：androidx.navigation
     *
     * 版本：https://developer.android.com/guide/navigation/navigation-getting-started
     */
    const val NAV = "2.7.7"

    /**
     * 建立Mock物件的套件
     *
     * Group：org.mockito.kotlin
     *
     * 版本：https://github.com/mockito/mockito-kotlin/releases
     */
    const val MOCKITO = "5.3.1"

    /**
     * Java HTML解析套件
     *
     * Group：org.jsoup
     *
     * 版本：https://mvnrepository.com/artifact/org.jsoup/jsoup
     */
    const val JSOUP = "1.17.2"

    /**
     * Kotlin Coroutines Test
     *
     * org.jetbrains.kotlinx:kotlinx-coroutines-test
     *
     * 版本：https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
     */
    const val COROUTINES_TEST = "1.8.1"

    /**
     * Android Apache PDFBox套件，原PDFBox因為有用到字型等原生Java AWT套件，Android是Google自行實作的，所以會沒辦法用
     *
     * 原套件：org.apache.pdfbox:pdfbox
     *
     * 原套件版本：https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
     *
     * com.tom-roush:pdfbox-android
     *
     * 版本：https://github.com/TomRoush/PdfBox-Android
     */
    const val ANDROID_APACHE_PDFBOX = "2.0.27.0"
}
