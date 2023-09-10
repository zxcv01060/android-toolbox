package tw.idv.louislee.toolbox.generator

import androidx.annotation.StringRes
import tw.idv.louislee.toolbox.R

enum class DatabaseConnectionStringSupportType(@StringRes val titleId: Int) {
    JAVA(R.string.generator_database_connection_string_java),
    CSHARP(R.string.generator_database_connection_string_c_sharp),
    DB_MATE(R.string.generator_database_connection_string_db_mate)
}

enum class DatabaseType(@StringRes val titleId: Int, val defaultPort: UInt) {
    MY_SQL(R.string.generator_database_connection_string_my_sql, 3306u),
    SQL_SERVER(R.string.generator_database_connection_string_sql_server, 1433u)
}