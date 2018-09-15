package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class AppConfigModule: AbstractModule() {

    override fun configure() { }

    @Provides
    fun getRouter(vertx: Vertx): Router {
        return Router.router(vertx)
    }

    @Provides
    @Singleton
    fun getVertx(): Vertx {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory")
        return Vertx.vertx()
    }

}