package io.learning.api

import com.google.inject.Guice
import io.learning.api.controllers.UserController
import io.learning.api.di.DatabaseModule
import io.learning.api.di.PropertiesModule
import io.learning.api.di.UserModule
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.flywaydb.core.Flyway
import javax.sql.DataSource

fun main(args: Array<String>) {
    val port = 8080

    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    val injector = Guice.createInjector(DatabaseModule(), UserModule(), PropertiesModule())

    val dataSource = injector.getInstance(DataSource::class.java)

    migrate(dataSource, clean = true)

    val userCtrl = injector.getInstance(UserController::class.java)

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