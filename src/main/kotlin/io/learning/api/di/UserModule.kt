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
    @Named("usersRouter")
    fun getUsersRouter(userController: UserController, usersRouter: Router): Router {
        usersRouter.get("/").blockingHandler(userController::findAll)
        usersRouter.get("/:id").blockingHandler(userController::findById)
        usersRouter.post("/").blockingHandler(userController::insertUser)
        usersRouter.put("/:id").blockingHandler(userController::updateUser)
        usersRouter.delete("/:id").blockingHandler(userController::deleteUser)
        return usersRouter
    }
}