package com.elramady.prayertimes.presentation.component


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.elramady.prayertimes.domain.models.PrayerTimesData
import com.elramady.prayertimes.presentation.prayers_times.component.PrayerTimesCard
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = 0.7f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }


@Composable
fun HorizontalCardSlider(
    modifier: Modifier,
    prayersTimeList: List<PrayerTimesData?>? = null,
    day: String,
    currentDay:(String,Int)->Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { prayersTimeList?.size ?: 0 })
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {


            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(horizontal = 30.dp),
                pageSpacing = 18.dp
            ) { page ->
                val prayerTimeDay = prayersTimeList?.get(page)
                Log.d("prayerTimeDay", prayerTimeDay.toString())

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(150.dp)
                ) {
                    PrayerTimesCard(
                        modifier = Modifier.carouselTransition(page, pagerState),
                        prayerTimeDataItem = prayerTimeDay
                    )
                }
            }



            LaunchedEffect(prayersTimeList, day) {
                coroutineScope.launch {
                    prayersTimeList?.let {

                        val indexItem = it.indexOfFirst { it?.day == day }
                        if (indexItem != -1) {  // Check if the item was found
                            pagerState.animateScrollToPage(indexItem)
                            Log.d("CurrentItem", "first")

                        } else {
                            Log.e("HorizontalCardSlider", "Day $day not found in the list.")
                        }
                    }
                }
            }

            LaunchedEffect(pagerState.currentPage) {
                // Optionally, you can also handle any actions when the current page changes
                val currentItem = prayersTimeList?.get(pagerState.currentPage)
                Log.d("CurrentItem", "Current Prayer Time Item: $currentItem")
//
                if (currentItem?.date !=null){
                    currentDay(currentItem.date.toString(),pagerState.currentPage)

                }


                // Do something with the current item if needed
            }


    }
}


//@Composable
//fun HorizontalCardSlider2(
//    modifier: Modifier,
//    prayersTimeList: List<PrayerTimesData?>? = null,
//    day: String,
//    ) {
//    val context = LocalContext.current
//    val pagerState = rememberPagerState(pageCount = { prayersTimeList?.size ?: 0 })
//
//
//
//
//    val coroutineScope = rememberCoroutineScope()
//    Box(
//        modifier = Modifier.fillMaxWidth(),
//        contentAlignment = Alignment.Center
////            .height(200.dp)
//    ) {
//        //    val containerWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxWidth(),
//               // .fillMaxHeight(),
//            verticalAlignment = Alignment.CenterVertically,
//            contentPadding = PaddingValues(horizontal = 30.dp),
//            pageSpacing = 18.dp
//        ) { page ->
//            val prayerTimeDay = prayersTimeList?.get(page)
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .width(150.dp)
//                    //.fillMaxHeight()
//            ) {
//                PrayerTimesCard(
//                    modifier = Modifier.carouselTransition(page, pagerState),
//                    prayerTimeDataItem = prayerTimeDay
//                    )
//            }
//
//
//
//
//        }
//
//        LaunchedEffect(Unit) {
//            coroutineScope.launch {
//
//                prayersTimeList?.let {
//                   val  indexItem = prayersTimeList.indexOf(prayersTimeList.find { it?.day == day })
//                    pagerState.animateScrollToPage(indexItem)
//
//                }
//
//                // Use animateScrollToPage to add animation
//
//                // Or use scrollToPage for instant scroll
//                // pagerState.scrollToPage(page)
//            }
//        }
//
//
//
//
//    }
//
//
//}
