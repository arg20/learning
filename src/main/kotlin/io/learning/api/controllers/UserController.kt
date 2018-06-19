package io.learning.api.controllers

import io.learning.api.services.UserService
import io.learning.api.utils.json
import io.vertx.ext.web.RoutingContext

class UserController(private val userService: UserService) {

    fun findAll(ctx: RoutingContext) {
        val users: Any = userService.findAll()
        ctx.json(users)
    }

}