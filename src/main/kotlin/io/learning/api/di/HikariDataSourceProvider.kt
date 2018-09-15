package io.learning.api.di

import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.name.Named
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class HikariDataSourceProvider @Inject constructor(
        @Named("db.learning.host") private val host: String,
        @Named("db.learning.port") private val port: Int,
        @Named("db.learning.dbName") private val dbName: String,
        @Named("db.learning.requireSSL") private val requireSSL: Boolean,
        @Named("db.learning.serverTimezone") private val serverTimezone: String,
        @Named("db.learning.allowPublicKeyRetrieval") private val allowPublicKeyRetrieval: Boolean,
        @Named("db.learning.user") private val user:String,
        @Named("db.learning.password") private val password: String,
        @Named("db.learning.connectionPoolSize") private val connectionPoolSize: Int): Provider<DataSource> {

    override fun get(): DataSource {
        val jdbcUrl = "jdbc:mysql://$host:$port/$dbName?verifyServerCertificate=$requireSSL&useSSL=$requireSSL&serverTimezone=$serverTimezone&allowPublicKeyRetrieval=$allowPublicKeyRetrieval"
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = jdbcUrl
        dataSource.username = user
        dataSource.password = password
        dataSource.maximumPoolSize = connectionPoolSize
        dataSource.addDataSourceProperty("cachePrepStmts", true)
        dataSource.addDataSourceProperty("prepStmtCacheSize", 250)
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048)

        return dataSource
    }

}