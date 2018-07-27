package io.learning.api

import com.google.inject.Guice
import io.learning.api.di.DatabaseModule
import io.learning.api.di.PropertiesModule
import io.learning.api.di.AppConfigModule
import io.learning.api.di.UserModule
import io.learning.api.utils.AppConfig
import io.vertx.core.Vertx
import org.flywaydb.core.Flyway
import javax.sql.DataSource

fun main(args: Array<String>) {

    val injector = Guice.createInjector(
            DatabaseModule(),
            UserModule(),
            PropertiesModule(),
            AppConfigModule())

    val appConfig = injector.getInstance(AppConfig::class.java)

    val server = appConfig.vertx.createHttpServer()

    val dataSource = injector.getInstance(DataSource::class.java)

    migrate(dataSource, clean = true)

    server.requestHandler(appConfig.mainRouter::accept).listen(appConfig.serverPort) {
        if (it.succeeded()) {
            println("Server started successfully. Listening on Port: ${appConfig.serverPort}")
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