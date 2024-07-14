package com.example.routes

import com.example.models.JobRequest
import com.example.services.DatabaseService.collection
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.litote.kmongo.eq
import io.ktor.http.*
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

fun Route.jobRoutes() {
    authenticate("auth-basic") {
        get<JobRequests> {
            try {
                call.respond(HttpStatusCode.OK, collection.find().toList())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to fetch job requests: ${e.message}")
            }
        }

        put("/job/{id}") {
            val id = call.parameters["id"]
            val request = call.receive<JobRequest>()
            try {
                collection.updateOne(JobRequest::id eq id, request)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to update job request: ${e.message}")
            }
        }
    }

    post("/job") {
        try {
            val request = call.receive<JobRequest>()
            collection.insertOne(request)
            call.respond(HttpStatusCode.Created, request)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.message}")
        }
    }

    get("/job/{id}") {
        val id = call.parameters["id"]
        val request = collection.findOne(JobRequest::id eq id)
        if (request != null) {
            call.respond(request)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}

@Location("/jobs")
class JobRequests
