package io.learning.api.controllers

import com.google.inject.Inject
import com.google.inject.Singleton
import com.google.inject.name.Named
import io.learning.api.entities.User
import io.learning.api.services.UserService
import io.learning.api.utils.json
import io.vertx.ext.web.RoutingContext

@Singleton
class UserController @Inject constructor (private val userService: UserService,
                                          @Named("router.users.path") val usersPath: String) {

    fun findAll(ctx: RoutingContext) {
        val users: Any = userService.findAll()
        ctx.json(users)
    }

    fun findById(ctx: RoutingContext) {
        val id = ctx.request().getParam("id").toInt()
        val user: User = userService.findById(id)
        ctx.json(user)
    }

    fun insertUser(ctx: RoutingContext) {
        val user: User = ctx.bodyAsJson.mapTo(User::class.java)
        val insertedUser = userService.insertUser(user)
        ctx.response().statusCode = 201
        ctx.response().putHeader("Location", "$usersPath/${insertedUser.id}")
        ctx.json(insertedUser)
    }

    fun updateUser(ctx: RoutingContext) {
        val user: User = ctx.bodyAsJson.mapTo(User::class.java)
        val id = ctx.request().getParam("id").toInt()
        val userToUpdate = user.copy(id=id)
        userService.updateUser(userToUpdate)
        ctx.response().statusCode = 204
        ctx.response().end()
    }

    fun deleteUser(ctx: RoutingContext) {
        val id = ctx.request().getParam("id").toInt()
        userService.deleteUser(id)
        ctx.response().statusCode = 204
        ctx.response().end()
    }

}