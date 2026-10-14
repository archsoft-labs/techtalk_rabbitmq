package com.engsoft.rabbitmq.application.services

import com.engsoft.rabbitmq.domain.interfaces.application.ProducerService
import com.engsoft.rabbitmq.domain.interfaces.infraestructure.AMQPProducer
import com.engsoft.rabbitmq.domain.models.Letter
import org.springframework.stereotype.Service

@Service
class ProducerService(
    val producer: AMQPProducer
) : ProducerService {
    override fun produce(letter: Letter) {
        producer.produce(letter)
    }
}
