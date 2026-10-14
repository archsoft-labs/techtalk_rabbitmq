package com.engsoft.rabbitmq.Infrastructure.mappers

import com.engsoft.rabbitmq.domain.interfaces.mappers.BaseMapper
import com.engsoft.rabbitmq.domain.models.Letter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.core.Message
import org.springframework.stereotype.Component

@Component
class LetterMapper() : BaseMapper<Message, Letter> {
    override fun from(message: Message): Letter {
        try {
            val payload = ObjectMapper().readTree(message.body)

            val letter = Letter(
                payload.get("text").toString()
            )

            return letter
        } catch (ex: Exception) {
            // Envia a mensagem para a Dead Letter Queue!
            throw AmqpRejectAndDontRequeueException(ex)
        }
    }
}
