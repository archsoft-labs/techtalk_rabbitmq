package com.engsoft.rabbitmq.Infrastructure.services.messaging.rabbitmq

import com.engsoft.rabbitmq.domain.interfaces.application.ConsumerService
import com.engsoft.rabbitmq.domain.interfaces.infraestructure.AMQPConsumer
import com.engsoft.rabbitmq.domain.interfaces.mappers.BaseMapper
import com.engsoft.rabbitmq.domain.models.Letter
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class RabbitMQConsumer(
    val consumerService: ConsumerService,
    val letterMapper: BaseMapper<Message, Letter>
) : AMQPConsumer {
    @RabbitListener(id = "letter-queue", queues = ["letter.queue"])
    override fun consume(message: Message) {
        consumerService.consume(
            letterMapper.from(message)
        )
    }
}