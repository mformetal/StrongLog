package mformetal.stronglog.common.deserialization

import kotlinx.serialization.json.JSON

fun <T : Any> String.toJson() = JSON.parse<T>(this)