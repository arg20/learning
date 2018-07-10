package io.learning.api.di

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import java.io.FileReader
import java.util.*

class PropertiesModule: AbstractModule() {
    override fun configure() {
        val properties = Properties()
        properties.load(FileReader(javaClass.classLoader.getResource("application.properties").file))
        Names.bindProperties(binder(), properties)
    }
}