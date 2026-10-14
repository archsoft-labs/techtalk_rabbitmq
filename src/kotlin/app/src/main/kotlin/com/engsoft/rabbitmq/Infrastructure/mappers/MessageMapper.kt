package com.engsoft.rabbitmq.Infrastructure.mappers

import com.engsoft.rabbitmq.domain.interfaces.mappers.BaseMapper
import com.engsoft.rabbitmq.domain.models.Letter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class MessageMapper : BaseMapper<Letter, Message> {
    override fun from(letter: Letter): Message {
        val properties = MessageProperties()
        properties.messageId = UUID.randomUUID().toString()
        properties.contentType = MessageProperties.CONTENT_TYPE_JSON
        properties.setHeader("createdAt", LocalDate.now().toString())

        val body = ObjectMapper().writeValueAsBytes(letter)

        return Message(body, properties)
    }
}
