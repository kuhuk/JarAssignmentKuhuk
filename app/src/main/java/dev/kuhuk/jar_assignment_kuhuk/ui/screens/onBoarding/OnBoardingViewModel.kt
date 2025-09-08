package dev.kuhuk.jar_assignment_kuhuk.ui.screens.onBoarding

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kuhuk.jar_assignment_kuhuk.io.retrofit.RetrofitInstance
import dev.kuhuk.jar_assignment_kuhuk.model.EducationCard
import dev.kuhuk.jar_assignment_kuhuk.model.SaveButtonCta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class OnBoardingViewModel @Inject constructor(): ViewModel() {
    private val _cards = MutableStateFlow<List<EducationCard>>(emptyList())
    val cards: StateFlow<List<EducationCard>> = _cards

    private val _ctaDetails = mutableStateOf(SaveButtonCta(
        text= "",
        deeplink= "",
        backgroundColor = "",
        textColor = "",
        strokeColor = "",
        icon = "",
        order = 0))
    val ctaDetails: MutableState<SaveButtonCta> = _ctaDetails

    private val _lottieUrl = mutableStateOf("")
    val lottieUrl: MutableState<String> = _lottieUrl

    fun fetchEducationCards() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEducationMetadata()
                _cards.value = response.data.manualBuyEducationData.educationCardList
                _ctaDetails.value = response.data.manualBuyEducationData.saveButtonCta
                _lottieUrl.value = response.data.manualBuyEducationData.ctaLottie
            } catch (e: Exception) {
                Log.d("logTag", e.message.toString())
                e.printStackTrace()
            }
        }
    }
}