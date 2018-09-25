package io.learning.api.utils

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.Inject
import com.google.inject.name.Named
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.ext.web.handler.LoggerHandler

class AppConfig @Inject constructor (
        val vertx: Vertx,
        val mainRouter: Router,
        @Named("usersRouter") usersRouter: Router,
        @Named("router.users.path") usersPath: String,
        @Named("http.server.port") val serverPort: Int) {

    init {
        Json.mapper.registerModule(KotlinModule())
        mainRouter.route().handler(BodyHandler.create())
        mainRouter.route().handler(LoggerHandler.create(LoggerFormat.TINY))
        mainRouter.mountSubRouter(usersPath, usersRouter)
    }

}