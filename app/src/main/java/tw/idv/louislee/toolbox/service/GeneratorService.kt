package tw.idv.louislee.toolbox.service

import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringBuilderFactory
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.generator.DatabaseType
import tw.idv.louislee.toolbox.generator.UnsupportedGeneratorException
import javax.inject.Inject
import javax.inject.Singleton

interface GeneratorService {
    fun generateConnectionString(state: DatabaseConnectionStringGeneratorState): String
}

@Singleton
class GeneratorServiceImpl @Inject constructor(
    private val connectionStringBuilderFactory: DatabaseConnectionStringBuilderFactory
) : GeneratorService {
    override fun generateConnectionString(state: DatabaseConnectionStringGeneratorState): String {
        val builder = connectionStringBuilderFactory.create(state.supportType)

        return when (state.databaseType) {
            DatabaseType.MY_SQL -> builder.buildMySql(state)
            DatabaseType.SQL_SERVER -> builder.buildSqlServer(state)
            null -> throw UnsupportedGeneratorException()
        }
    }
}
