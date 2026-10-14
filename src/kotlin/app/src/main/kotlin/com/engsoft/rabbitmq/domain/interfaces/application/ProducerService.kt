package com.engsoft.rabbitmq.domain.interfaces.application

import com.engsoft.rabbitmq.domain.models.Letter

interface ProducerService {
    fun produce(letter: Letter)
}
