package io.learning.api.db

import io.learning.api.entities.User
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface UserDao {

    @SqlQuery("SELECT * FROM users")
    fun selectAll(): Set<User>

    @SqlQuery("SELECT * FROM users WHERE id=:id")
    fun selectById(id: Int): User

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO users(first_name, last_name, email, age) VALUES (:firstName, :lastName, :email, :age)")
    fun insertUser(@BindBean user: User): Int

    @SqlUpdate("UPDATE users SET first_name=:firstName, last_name=:lastName, email=:email, age=:age WHERE id=:id")
    fun updateUser(@BindBean user: User)

    @SqlUpdate("DELETE FROM users WHERE id=:id")
    fun deleteUser(id: Int)

}