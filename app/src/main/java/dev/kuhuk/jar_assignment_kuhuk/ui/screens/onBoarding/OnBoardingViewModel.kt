package dev.kuhuk.jar_assignment_kuhuk.ui.screens.onBoarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kuhuk.jar_assignment_kuhuk.io.retrofit.RetrofitInstance
import dev.kuhuk.jar_assignment_kuhuk.model.EducationCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(): ViewModel() {
    private val _cards = MutableStateFlow<List<EducationCard>>(emptyList())
    val cards: StateFlow<List<EducationCard>> = _cards

    fun fetchEducationCards() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEducationMetadata()
                _cards.value = response.data.manualBuyEducationData.educationCardList
            } catch (e: Exception) {
                Log.d("logTag", e.message.toString())
                e.printStackTrace()
            }
        }
    }
}