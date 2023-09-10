package tw.idv.louislee.toolbox.generator

import tw.idv.louislee.toolbox.extensions.encodeUrl
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringSupportType.CSHARP
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringSupportType.DB_MATE
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringSupportType.JAVA
import javax.inject.Inject
import javax.inject.Singleton

interface DatabaseConnectionStringBuilder {
    fun buildMySql(state: DatabaseConnectionStringGeneratorState): String
    fun buildSqlServer(state: DatabaseConnectionStringGeneratorState): String
}

fun interface DatabaseConnectionStringBuilderFactory {
    fun create(supportType: DatabaseConnectionStringSupportType): DatabaseConnectionStringBuilder
}

@Singleton
class DatabaseConnectionStringBuilderFactoryImpl @Inject constructor() :
    DatabaseConnectionStringBuilderFactory {
    override fun create(supportType: DatabaseConnectionStringSupportType): DatabaseConnectionStringBuilder =
        when (supportType) {
            JAVA -> JavaDatabaseConnectionStringBuilder()
            CSHARP -> CSharpDatabaseConnectionStringBuilder()
            DB_MATE -> DbMateDatabaseConnectionStringBuilder()
        }
}

class JavaDatabaseConnectionStringBuilder : DatabaseConnectionStringBuilder {
    override fun buildMySql(state: DatabaseConnectionStringGeneratorState): String {
        val properties = state.getProperties<DatabaseConnectionStringProperty.Java.MySql>()

        return "jdbc:mysql://${state.host}:${state.port}/${state.database}" +
                if (properties.isNotEmpty()) {
                    properties.joinToString(separator = ";") { "${it.key.propertyName}=${it.value}" }
                } else {
                    ""
                }
    }

    override fun buildSqlServer(state: DatabaseConnectionStringGeneratorState): String {
        val properties = state.getProperties<DatabaseConnectionStringProperty.Java.SqlServer>()

        return "jdbc:microsoft:sqlserver://${state.host}:${state.port};DatabaseName=${state.database}" +
                if (properties.isNotEmpty()) {
                    properties.joinToString(separator = ";") { "${it.key.propertyName}=${it.value}" }
                } else {
                    ""
                }
    }
}

class CSharpDatabaseConnectionStringBuilder : DatabaseConnectionStringBuilder {
    override fun buildMySql(state: DatabaseConnectionStringGeneratorState): String = build(
        state,
        state.getProperties<DatabaseConnectionStringProperty.CSharp.MySql>()
    )

    private fun build(
        state: DatabaseConnectionStringGeneratorState,
        properties: List<Map.Entry<DatabaseConnectionStringProperty.CSharp, String>>
    ): String {
        val connectionInfoProperties = arrayOf(
            "Server" to state.host,
            "Port" to state.port.toString(),
            "User Id" to state.account,
            "Password" to state.password,
            "Database" to state.database
        )
        val allProperties = connectionInfoProperties.map { "${it.first}=${it.second}" } +
                properties.map { "${it.key.propertyName}=${it.value}" }

        return allProperties.joinToString(separator = ";")
    }

    override fun buildSqlServer(state: DatabaseConnectionStringGeneratorState): String = build(
        state,
        state.getProperties<DatabaseConnectionStringProperty.CSharp.SqlServer>()
    )
}

class DbMateDatabaseConnectionStringBuilder : DatabaseConnectionStringBuilder {
    override fun buildMySql(state: DatabaseConnectionStringGeneratorState): String = build(
        "mysql",
        state,
        state.getProperties<DatabaseConnectionStringProperty.DbMate.MySql>()
    )

    private fun build(
        schema: String,
        state: DatabaseConnectionStringGeneratorState,
        properties: List<Map.Entry<DatabaseConnectionStringProperty.DbMate, String>>
    ): String {
        val account = state.account.encodeUrl()
        val password = state.password.encodeUrl()

        return "$schema://$account:$password@${state.host}:${state.port}/${state.database}"
    }

    override fun buildSqlServer(state: DatabaseConnectionStringGeneratorState): String = build(
        "sqlserver",
        state,
        state.getProperties<DatabaseConnectionStringProperty.DbMate.SqlServer>()
    )
}
