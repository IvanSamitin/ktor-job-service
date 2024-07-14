package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class JobRequest(
    @BsonId val id: String = ObjectId().toString(),
    val initials: String,
    val phone: String,
    val company: String,
    val desiredDate: String,
    val status: String = "pending"
)
