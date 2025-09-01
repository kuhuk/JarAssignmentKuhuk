package dev.kuhuk.jar_assignment_kuhuk.io.retrofit

import dev.kuhuk.jar_assignment_kuhuk.model.EducationResponse
import retrofit2.http.GET

interface ApiService {
    @GET("education-metadata.json")
    suspend fun getEducationMetadata(): EducationResponse
}