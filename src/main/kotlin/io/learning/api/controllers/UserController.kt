package io.learning.api.controllers

import io.vertx.ext.web.RoutingContext

interface UserController {

    fun findAll(ctx: RoutingContext)

}