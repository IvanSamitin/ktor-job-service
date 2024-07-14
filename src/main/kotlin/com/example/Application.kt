package com.example

import com.example.auth.configureAuthentication
import com.example.routes.jobRoutes
import com.example.services.DatabaseService
import com.example.services.DatabaseService.configureDatabase
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.sessions.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        configure()
    }.start(wait = true)
}

fun Application.configure() {
    install(ContentNegotiation) {
        json()
    }
    install(Sessions) {
        cookie<UserSession>("user_session")
    }
    install(Locations)
    configureAuthentication()
    DatabaseService.run { configureDatabase() }
    routing {
        jobRoutes()
    }
}



@kotlinx.serialization.Serializable
data class UserSession(val id: String)
