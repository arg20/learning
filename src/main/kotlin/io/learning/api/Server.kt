package io.learning.api

import com.google.inject.Guice
import io.learning.api.controllers.UserController
import io.learning.api.db.UserDao
import io.learning.api.di.DatabaseModule
import io.learning.api.services.UserService
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.flywaydb.core.Flyway
import org.jdbi.v3.core.Jdbi
import javax.sql.DataSource

fun main(args: Array<String>) {
    val port = 8080

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val injector = Guice.createInjector(DatabaseModule())

    val router = Router.router(vertx)

    val dataSource = injector.getInstance(DataSource::class.java)

    val dbi = injector.getInstance(Jdbi::class.java)
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

fun migrate(dataSource: DataSource, baseline: Boolean = true, clean: Boolean = false) {
    val flyway = Flyway()
    flyway.dataSource = dataSource
    flyway.isBaselineOnMigrate = baseline
    if (clean) {
        flyway.clean()
    }
    flyway.migrate()
}