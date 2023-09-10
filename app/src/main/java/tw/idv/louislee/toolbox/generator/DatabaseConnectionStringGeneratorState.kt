package tw.idv.louislee.toolbox.generator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DatabaseConnectionStringGeneratorState {
    var supportType by mutableStateOf(DatabaseConnectionStringSupportType.JAVA)
    var host by mutableStateOf("")
    var port by mutableStateOf(0u)
    var databaseType by mutableStateOf<DatabaseType?>(null)
    var database by mutableStateOf("")
    var account by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordEncodedByBase64 by mutableStateOf(false)
    val properties = hashMapOf<DatabaseConnectionStringProperty, String>()

    fun remove(property: DatabaseConnectionStringProperty) {
        properties.remove(property)
    }

    inline fun <reified T : DatabaseConnectionStringProperty> getProperties(): List<Map.Entry<T, String>> =
        properties.filterKeys { it is T }
            .mapKeys { it.key as T }
            .map { it }

    operator fun get(property: DatabaseConnectionStringProperty): String? = properties[property]

    operator fun set(property: DatabaseConnectionStringProperty, value: String?) {
        if (value == null) {
            remove(property)
            return
        }

        properties[property] = value
    }
}

sealed interface DatabaseConnectionStringProperty {
    val propertyName: String

    sealed interface Java : DatabaseConnectionStringProperty {
        enum class MySql(override val propertyName: String) : Java {
        }

        enum class SqlServer(override val propertyName: String) : Java {

        }
    }

    sealed interface CSharp : DatabaseConnectionStringProperty {
        enum class MySql(override val propertyName: String) : CSharp {
            TINY_INT_AS_BOOLEAN("tinyIntAsBoolean"),
            CONNECTION_TIMEOUT("Connection Timeout")
        }

        enum class SqlServer(override val propertyName: String) : CSharp {

        }
    }

    sealed interface DbMate : DatabaseConnectionStringProperty {
        enum class MySql(override val propertyName: String) : DbMate {
        }

        enum class SqlServer(override val propertyName: String) : DbMate {

        }
    }
}
