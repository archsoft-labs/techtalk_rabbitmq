package com.engsoft.rabbitmq.presentation.controllers

import com.engsoft.rabbitmq.domain.interfaces.application.ProducerService
import com.engsoft.rabbitmq.domain.models.Letter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@ResponseStatus(HttpStatus.ACCEPTED)
@RequestMapping("/message")
class MessageController(
    val producerService: ProducerService
) {
    @PostMapping("publish")
    fun publish(@RequestBody letter: Letter) {
        producerService.produce(letter)
    }
}
