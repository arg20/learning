package io.learning.api.controllers

import com.google.inject.Inject
import io.learning.api.services.UserService
import io.learning.api.utils.json
import io.vertx.ext.web.RoutingContext

class UserControllerImpl @Inject constructor (private val userService: UserService): UserController {

    override fun findAll(ctx: RoutingContext) {
        val users: Any = userService.findAll()
        ctx.json(users)
    }

}