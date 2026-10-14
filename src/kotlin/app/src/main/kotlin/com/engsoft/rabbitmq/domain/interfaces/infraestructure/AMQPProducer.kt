package com.engsoft.rabbitmq.domain.interfaces.infraestructure

import com.engsoft.rabbitmq.domain.models.Letter

interface AMQPProducer {
    fun produce(letter: Letter)
}
