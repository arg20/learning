package io.learning.api.services

import io.learning.api.db.UserDao
import io.learning.api.entities.User

class UserService(private val userDao: UserDao) {

    fun findAll(): Set<User> {
        return userDao.selectAll()
    }

    fun findById(id: Int): User {
        return userDao.selectById(id)
    }

}