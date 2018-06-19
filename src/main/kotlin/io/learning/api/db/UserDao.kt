package io.learning.api.db

import io.learning.api.entities.User
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface UserDao {

    @SqlQuery("SELECT * FROM users")
    fun selectAll(): Set<User>

    @SqlQuery("SELECT * FROM users WHERE id=:id")
    fun selectById(id: Int): User

}