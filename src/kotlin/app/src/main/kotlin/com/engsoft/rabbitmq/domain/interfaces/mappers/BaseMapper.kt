package com.engsoft.rabbitmq.domain.interfaces.mappers

import org.springframework.stereotype.Component

@Component
interface BaseMapper<TOrigin, TDestiny> {
    fun from(origin: TOrigin): TDestiny
}