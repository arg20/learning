package io.learning.api.services

import io.learning.api.entities.User

interface UserService {

    fun findAll(): Set<User>

    fun findById(id: Int): User

}