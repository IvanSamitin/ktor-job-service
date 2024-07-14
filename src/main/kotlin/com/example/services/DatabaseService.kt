package com.example.services

import com.example.models.JobRequest
import io.ktor.server.application.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

object DatabaseService {
    private val client = KMongo.createClient()
    private val database = client.getDatabase("job_requests")
    val collection = database.getCollection<JobRequest>()

    fun Application.configureDatabase() {
        log.info("Database connected: ${database.name}")
        // Добавьте любую дополнительную настройку или инициализацию, если нужно
    }

}