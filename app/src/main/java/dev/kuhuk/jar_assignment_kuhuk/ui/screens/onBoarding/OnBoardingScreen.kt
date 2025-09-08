package dev.kuhuk.jar_assignment_kuhuk.ui.screens.onBoarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import dev.kuhuk.jar_assignment_kuhuk.R
import dev.kuhuk.jar_assignment_kuhuk.model.EducationCard
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.kuhuk.jar_assignment_kuhuk.model.SaveButtonCta
import dev.kuhuk.jar_assignment_kuhuk.navigation.Routes

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(navController: NavController, viewModel: OnBoardingViewModel = viewModel()) {
    val cards by viewModel.cards.collectAsState()
    val saveButtonCTA by viewModel.ctaDetails
    val lottieUrl by viewModel.lottieUrl

    LaunchedEffect(Unit) {
        viewModel.fetchEducationCards()
    }

    if (cards.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        CollapsiblePager(navController, pages = cards, saveButtonCTA = saveButtonCTA, lottieUrl)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CollapsiblePager(
    navController: NavController,
    pages: List<EducationCard>,
    saveButtonCTA: SaveButtonCta,
    lottieUrl: String
) {
    val pagerState = rememberPagerState()
    var expandedIndex by remember { mutableStateOf<Int?>(null) }
    var isCTAShown = remember { mutableStateOf(false) }

    val bgColor by animateColorAsState(
        targetValue = Color(android.graphics.Color.parseColor(pages[pagerState.currentPage].backGroundColor)),
        label = "bgColorAnim"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Text(
                        text = "Onboarding",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                },
                navigationIcon = {
                    val coroutineScope = rememberCoroutineScope()
                    IconButton(onClick = { coroutineScope.launch { onBack(pagerState) } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back_button),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        backgroundColor = bgColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(pages[pagerState.currentPage].backGroundColor.toColorInt()))
        ) {
            // reset expand index when page changes
            LaunchedEffect(pagerState.currentPage) {
                expandedIndex = null
            }

            VerticalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                val page = pages[pageIndex]
                val isLastPage = pageIndex == pages.lastIndex
                val anyCollapsedExpanded = expandedIndex != null

                if (!isLastPage || (isLastPage && !anyCollapsedExpanded)) {
                    EducationCardUI(
                        strokeStartColor = Color(page.strokeStartColor.toColorInt()),
                        strokeEndColor = Color(page.strokeEndColor.toColorInt()),
                        startGradient = Color(page.startGradient.toColorInt()),
                        endGradient = Color(page.endGradient.toColorInt()),
                        imageUrl = page.image,
                        expandStateText = page.expandStateText
                    )
                }
            }

            // Collapsed cards
            Column(modifier = Modifier.fillMaxWidth()) {
                val indicesToShow = when {
                    pagerState.currentPage == pages.lastIndex && expandedIndex != null -> {
                        pages.indices
                    }
                    else -> {
                        pages.indices.take(pagerState.currentPage)
                    }
                }

                indicesToShow.forEach { pageIndex ->
                    val page = pages[pageIndex]
                    val isExpanded = expandedIndex == pageIndex

                    val strokeStartColor = Color(page.strokeStartColor.toColorInt())
                    val strokeEndColor = Color(page.strokeEndColor.toColorInt())
                    val startGradientColor = Color(page.startGradient.toColorInt())
                    val endGradientColor = Color(page.endGradient.toColorInt())

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shadow(4.dp, RoundedCornerShape(12.dp))
                            .animateContentSize(),
                        backgroundColor = startGradientColor
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                page.image?.let { res ->
                                    AsyncImage(
                                        model = res,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    text = page.collapsedStateText,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 16.dp),
                                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                                    color = Color.White,
                                )

                                IconButton(onClick = {
                                    expandedIndex = if (isExpanded) null else pageIndex
                                }) {
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }

                            if (isExpanded) {
                                EducationCardUI(
                                    strokeStartColor = strokeStartColor,
                                    strokeEndColor = strokeEndColor,
                                    startGradient = startGradientColor,
                                    endGradient = endGradientColor,
                                    imageUrl = page.image,
                                    expandStateText = page.expandStateText
                                )
                            }
                        }
                    }
                }
            }

            // Save CTA button
            if (pagerState.currentPage == pages.lastIndex || isCTAShown.value) {
                isCTAShown.value = true
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 24.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    SaveCTA(
                        lottieUrl = lottieUrl,
                        saveButtonCta = saveButtonCTA,
                        onClick = { navController.navigate(Routes.landingScreen().route) }
                    )
                }
            }
        }
    }
}

@Composable
fun EducationCardUI(strokeStartColor: Color,
                    strokeEndColor: Color,
                    startGradient: Color,
                    endGradient: Color,
                    imageUrl: String,
                    expandStateText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            strokeStartColor,
                            strokeEndColor
                        )
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            startGradient,
                            endGradient
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(340.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = expandStateText,
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun SaveCTA(
    lottieUrl: String,
    saveButtonCta: SaveButtonCta,
    onClick: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(lottieUrl)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(saveButtonCta.backgroundColor.toColorInt()),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color(saveButtonCta.strokeColor.toColorInt())
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = saveButtonCta.text,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(saveButtonCta.textColor.toColorInt())
                )
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
suspend fun onBack(pagerState: PagerState) {
    if (pagerState.currentPage > 0) {
        pagerState.animateScrollToPage(pagerState.currentPage - 1)
    }
}