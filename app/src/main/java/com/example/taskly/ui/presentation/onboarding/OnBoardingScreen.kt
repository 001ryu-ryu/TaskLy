package com.example.taskly.ui.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(onFinished: () -> Unit) {
    val pages = listOf(
        OnBoardingModel.FirstPage,
        OnBoardingModel.SecondPage,
        OnBoardingModel.ThirdPage
    )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

    val buttonState = remember {
        derivedStateOf {
            when(pagerState.currentPage) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Go")
                else -> listOf("", "")
            }
        }
    }

    val isBackButtonEnabled = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 35.dp)
                    ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonUi(title = buttonState.value[0],
                    backgroundColor = Color.Transparent,
                        textColor = Color.Gray
                    ) {
                        if (pagerState.currentPage > 0) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    IndicatorUi(
                        pageSize = pages.size,
                        currentPage = pagerState.currentPage
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonUi(buttonState.value[1]) {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                            else {
                                onFinished()
                            }
                        }

                    }
                }

            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            HorizontalPager(
                state = pagerState
            ) {index ->
                OnBoardingGraphUi(onBoardingModel = pages[index])
            }


        }
    }


}

@Preview(showBackground = true)
@Composable
private fun MainSc() {
    OnBoardingScreen {  }
}

























