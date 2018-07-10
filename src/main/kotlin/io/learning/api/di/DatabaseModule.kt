package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import javax.sql.DataSource

class DatabaseModule : AbstractModule() {
    override fun configure() {
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