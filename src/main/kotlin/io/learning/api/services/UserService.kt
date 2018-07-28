package io.learning.api.services

import com.google.inject.Inject
import com.google.inject.Singleton
import io.learning.api.db.UserDao
import io.learning.api.entities.User

@Singleton
class UserService @Inject constructor (private val userDao: UserDao) {

    fun findAll(): Set<User> {
        return userDao.selectAll()
    }

    fun findById(id: Int): User {
        return userDao.selectById(id)
    }

}