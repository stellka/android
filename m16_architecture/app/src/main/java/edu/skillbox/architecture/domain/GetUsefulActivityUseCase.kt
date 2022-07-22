package edu.skillbox.architecture.domain

import edu.skillbox.architecture.data.UsefulActivitiesRepository
import edu.skillbox.architecture.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val usefulActivitiesRepository : UsefulActivitiesRepository
) {
    suspend fun execute(): UsefulActivity {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}