package io.learning.api.controllers

import com.google.inject.Inject
import com.google.inject.Singleton
import io.learning.api.services.UserService
import io.learning.api.utils.json
import io.vertx.ext.web.RoutingContext

@Singleton
class UserController @Inject constructor (private val userService: UserService) {

    fun findAll(ctx: RoutingContext) {
        val users: Any = userService.findAll()
        ctx.json(users)
    }

}