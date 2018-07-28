package io.learning.api.utils

import com.google.inject.Inject
import com.google.inject.name.Named
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

class AppConfig @Inject constructor (
        val vertx: Vertx,
        val mainRouter: Router,
        @Named("userRouter") userRouter: Router,
        @Named("http.server.port") val serverPort: Int) {

    init {
        mainRouter.mountSubRouter("/users", userRouter)
    }

}