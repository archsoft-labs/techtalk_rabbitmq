package com.engsoft.rabbitmq.application.services

import com.engsoft.rabbitmq.domain.interfaces.application.ConsumerService
import com.engsoft.rabbitmq.domain.models.Letter
import org.springframework.stereotype.Service

@Service
class ConsumerService : ConsumerService {
    override fun consume(letter: Letter) {
        println("===================== LETTER =====================")
        println(letter.text)
        println("==================================================")
    }
}