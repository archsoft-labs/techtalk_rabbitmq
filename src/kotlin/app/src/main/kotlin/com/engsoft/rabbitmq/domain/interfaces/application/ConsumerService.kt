package com.engsoft.rabbitmq.domain.interfaces.application

import com.engsoft.rabbitmq.domain.models.Letter

interface ConsumerService {
    fun consume(letter: Letter)
}
