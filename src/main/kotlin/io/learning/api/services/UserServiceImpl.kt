package io.learning.api.services

import com.google.inject.Inject
import io.learning.api.db.UserDao
import io.learning.api.entities.User

class UserServiceImpl @Inject constructor (private val userDao: UserDao): UserService {

    override fun findAll(): Set<User> {
        return userDao.selectAll()
    }

    override fun findById(id: Int): User {
        return userDao.selectById(id)
    }

}