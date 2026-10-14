package com.engsoft.rabbitmq.Infrastructure.services.messaging.rabbitmq.configurations

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class RabbitMQMigration(
    private val rabbitAdmin: RabbitAdmin,
    @Value("\${app.messaging.rabbitmq.letter.exchange}") private val exchange: String,
    @Value("\${app.messaging.rabbitmq.letter.queue}") private val queue: String,
    @Value("\${app.messaging.rabbitmq.letter.queue-routing-key}") private val queueRoutingKey: String,
    @Value("\${app.messaging.rabbitmq.letter.dead-letter-queue}") private val deadLetterQueue: String,
    @Value("\${app.messaging.rabbitmq.letter.dead-letter-queue-routing-key}") private val deadLetterQueueRoutingKey: String
) {
    @PostConstruct
    fun createEntities() {
        createQueue()
        createDeadLetterQueue()
        createDirectExchange()
        createQueueBinding()
        createDeadLetterQueueBinding()
    }

    private fun createQueue() {
        val queue = QueueBuilder
            .durable(queue)
            .deadLetterExchange(exchange)
            .deadLetterRoutingKey(deadLetterQueueRoutingKey)
            .build()

        rabbitAdmin.declareQueue(queue)
    }

    private fun createDeadLetterQueue() {
        val deadLetterQueue = QueueBuilder
            .durable(deadLetterQueue)
            .build()

        rabbitAdmin.declareQueue(deadLetterQueue)
    }

    private fun createDirectExchange() {
        val directExchange = ExchangeBuilder
            .directExchange(exchange)
            .durable(true)
            .build<Exchange>()

        rabbitAdmin.declareExchange(directExchange)
    }

    private fun createQueueBinding() {
        val binding = Binding(
            queue,
            Binding.DestinationType.QUEUE,
            exchange,
            queueRoutingKey,
            null
        )

        rabbitAdmin.declareBinding(binding)
    }

    private fun createDeadLetterQueueBinding() {
        val binding = Binding(
            deadLetterQueue,
            Binding.DestinationType.QUEUE,
            exchange,
            deadLetterQueueRoutingKey,
            null
        )

        rabbitAdmin.declareBinding(binding)
    }
}
