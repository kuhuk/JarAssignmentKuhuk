package dev.kuhuk.jar_assignment_kuhuk.repository

import dev.kuhuk.jar_assignment_kuhuk.io.retrofit.RetrofitInstance
import dev.kuhuk.jar_assignment_kuhuk.model.EducationResponse

class EducationRepository {
    suspend fun fetchEducationData(): EducationResponse {
        return RetrofitInstance.api.getEducationMetadata()
    }
}