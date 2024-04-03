package com.ortin

import com.ortin.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = SERVER_HOST, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureRouting()
}

const val SERVER_PORT = 8080
const val SERVER_HOST = "0.0.0.0"
const val GENERAL_SERVER_ROUTE = "http://$SERVER_HOST:$SERVER_PORT"
