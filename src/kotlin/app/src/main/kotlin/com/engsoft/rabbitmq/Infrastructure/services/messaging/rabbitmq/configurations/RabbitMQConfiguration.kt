package com.engsoft.rabbitmq.Infrastructure.services.messaging.rabbitmq.configurations

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfiguration(
    val connection: ConnectionFactory,
    val listenerConfigurator: SimpleRabbitListenerContainerFactoryConfigurer,
    val listener: SimpleRabbitListenerContainerFactory
) {
    @Bean
    fun createAdmin(): RabbitAdmin {
        return RabbitAdmin(connection)
    }

    @Bean
    fun createListener(
    ): SimpleRabbitListenerContainerFactory? {
        listenerConfigurator.configure(listener, connection)
        return listener
    }

    @Bean
    fun createTemplate(
    ): RabbitTemplate {
        return RabbitTemplate(connection)
    }
}
