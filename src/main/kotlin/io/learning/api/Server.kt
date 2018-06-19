package io.learning.api

import com.zaxxer.hikari.HikariDataSource
import io.learning.api.controllers.UserController
import io.learning.api.db.UserDao
import io.learning.api.services.UserService
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.flywaydb.core.Flyway
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import javax.sql.DataSource

fun main(args: Array<String>) {
    val port = 8080

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    val dataSource = getDataSource()
    val dbi = getDbi(dataSource)
    migrate(dataSource, clean = true)

    val userDao = dbi.onDemand(UserDao::class.java)
    val userService = UserService(userDao)
    val userCtrl = UserController(userService)

    router.get("/users").blockingHandler(userCtrl::findAll)

    server.requestHandler(router::accept).listen(port) {
        if (it.succeeded()) {
            println("Server started successfully. Listening on Port: $port")
        } else{
            println(it.cause().message)
        }
    }
}

fun getDataSource(): DataSource {
    val host = "127.0.0.1"
    val port = "3306"
    val dbName = "learning"
    val requireSSL = false
    val user = "root"
    val password = ""
    val connectionPoolSize = 4

    val jdbcUrl = "jdbc:mysql://$host:$port/$dbName?verifyServerCertificate=$requireSSL&useSSL=$requireSSL&serverTimezone=UTC"
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

fun migrate(dataSource: DataSource, baseline: Boolean = true, clean: Boolean = false) {
    val flyway = Flyway()
    flyway.dataSource = dataSource
    flyway.isBaselineOnMigrate = baseline
    if (clean) {
        flyway.clean()
    }
    flyway.migrate()
}

fun getDbi(dataSource: DataSource): Jdbi {
    val jdbi = Jdbi.create(dataSource)
    jdbi.installPlugin(SqlObjectPlugin())
    jdbi.installPlugin(KotlinPlugin())
    jdbi.installPlugin(KotlinSqlObjectPlugin())
    return jdbi
}