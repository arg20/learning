package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import io.learning.api.controllers.UserController
import io.learning.api.db.UserDao
import io.vertx.ext.web.Router
import org.jdbi.v3.core.Jdbi
import javax.inject.Named

class UserModule: AbstractModule() {
    override fun configure() { }

    @Provides
    fun getUserDao(dbi: Jdbi): UserDao {
        return dbi.onDemand(UserDao::class.java)
    }

    @Provides
    @Named("userRouter")
    fun getUserRouter(userController: UserController, userRouter: Router): Router {
        userRouter.get("/").blockingHandler(userController::findAll)
        return userRouter
    }
}