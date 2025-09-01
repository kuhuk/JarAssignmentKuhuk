package dev.kuhuk.jar_assignment_kuhuk.model

data class EducationResponse(
    val success: Boolean,
    val data: EducationDataWrapper
)

data class EducationDataWrapper(
    val manualBuyEducationData: ManualBuyEducationData
)

data class ManualBuyEducationData(
    val toolBarText: String,
    val introTitle: String,
    val introSubtitle: String,
    val educationCardList: List<EducationCard>,
    val saveButtonCta: SaveButtonCta,
    val ctaLottie: String,
    val screenType: String,
    val cohort: String?,
    val combination: String?,
    val collapseCardTiltInterval: Int,
    val collapseExpandIntroInterval: Int,
    val bottomToCenterTranslationInterval: Int,
    val expandCardStayInterval: Int,
    val seenCount: Int?,
    val actionText: String,
    val shouldShowOnLandingPage: Boolean,
    val toolBarIcon: String,
    val introSubtitleIcon: String,
    val shouldShowBeforeNavigating: Boolean
)

data class EducationCard(
    val image: String,
    val collapsedStateText: String,
    val expandStateText: String,
    val backGroundColor: String,
    val strokeStartColor: String,
    val strokeEndColor: String,
    val startGradient: String,
    val endGradient: String
)

data class SaveButtonCta(
    val text: String,
    val deeplink: String?,
    val backgroundColor: String,
    val textColor: String,
    val strokeColor: String,
    val icon: String?,
    val order: Int?
)