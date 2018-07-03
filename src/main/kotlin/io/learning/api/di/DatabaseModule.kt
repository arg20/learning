package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.name.Names
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import java.io.FileReader
import java.util.*
import javax.sql.DataSource

class DatabaseModule : AbstractModule() {
    override fun configure() {
        val properties = Properties()
        properties.load(FileReader(javaClass.classLoader.getResource("application.properties").file))
        Names.bindProperties(binder(), properties)
        bind(DataSource::class.java).toProvider(HikariDataSourceProvider::class.java)
    }

    @Provides
    fun getJdbi(dataSource: DataSource): Jdbi  {
        val jdbi = Jdbi.create(dataSource)
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())
        return jdbi
    }
}