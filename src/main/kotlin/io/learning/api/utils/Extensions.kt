package io.learning.api.utils

import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

fun RoutingContext.json(payload: Any) {
    this.response().end(Json.encodePrettily(payload))
}