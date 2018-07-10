package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.Provides
import io.learning.api.controllers.UserController
import io.learning.api.controllers.UserControllerImpl
import io.learning.api.db.UserDao
import io.learning.api.services.UserService
import io.learning.api.services.UserServiceImpl
import org.jdbi.v3.core.Jdbi

class UserModule: AbstractModule() {
    override fun configure() {
        bind(UserController::class.java).to(UserControllerImpl::class.java)
        bind(UserService::class.java).to(UserServiceImpl::class.java)
    }

    @Provides
    fun getUserDao(dbi: Jdbi): UserDao {
        return dbi.onDemand(UserDao::class.java)
    }
}