package com.engsoft.rabbitmq.Infrastructure.services.messaging.rabbitmq

import com.engsoft.rabbitmq.domain.interfaces.infraestructure.AMQPProducer
import com.engsoft.rabbitmq.domain.interfaces.mappers.BaseMapper
import com.engsoft.rabbitmq.domain.models.Letter
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
public class RabbitMQProducer(
    private val template: RabbitTemplate,
    private val messageMapper: BaseMapper<Letter, Message>,
    @Value("\${app.messaging.rabbitmq.letter.exchange}") private val exchange: String,
    @Value("\${app.messaging.rabbitmq.letter.queue-routing-key}") private val queueRoutingKey: String
) : AMQPProducer {
    public override fun produce(letter: Letter) {
        template.convertAndSend(
            exchange,
            queueRoutingKey,
            messageMapper.from(letter)
        )
    }
}
