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

    fun insertUser(user: User): User {
        var id = userDao.insertUser(user)
        return user.copy(id=id)
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }

    //TODO partial updates

}