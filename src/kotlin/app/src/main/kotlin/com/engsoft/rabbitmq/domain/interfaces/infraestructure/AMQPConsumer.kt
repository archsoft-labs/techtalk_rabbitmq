package com.engsoft.rabbitmq.domain.interfaces.infraestructure

import org.springframework.amqp.core.Message

interface AMQPConsumer {
    fun consume(message: Message)
}
