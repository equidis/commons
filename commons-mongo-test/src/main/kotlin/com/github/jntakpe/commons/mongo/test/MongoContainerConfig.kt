package com.github.jntakpe.commons.mongo.test

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.micronaut.configuration.mongo.core.DefaultMongoConfiguration
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import javax.inject.Singleton

@Factory
class MongoContainerConfig {

    @Singleton
    @Bean(preDestroy = "close")
    @Replaces(bean = MongoClient::class)
    fun mongoContainerClient(initConfig: DefaultMongoConfiguration): MongoClient {
        val container = MongoContainer.instance
        val testConfig = initConfig.apply { uri = "mongodb://${container.containerIpAddress}:${container.firstMappedPort}" }
        return MongoClients.create(testConfig.buildSettings())
    }
}
