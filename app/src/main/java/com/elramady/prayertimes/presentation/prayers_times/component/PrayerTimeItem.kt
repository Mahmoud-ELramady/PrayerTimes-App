package com.elramady.prayertimes.presentation.prayers_times.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elramady.prayertimes.R
import com.elramady.prayertimes.common.DateUtils.convertTo12HourFormat
import com.elramady.prayertimes.domain.models.PrayerTimesData
import com.elramady.prayertimes.presentation.component.SpacerHeight
import com.elramady.prayertimes.presentation.component.SpacerWidth
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor

@Composable
fun PrayerTimeItem(
    modifier: Modifier = Modifier,
    prayerName:String,
    painter: Painter,
    time:String,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(15.dp))
            .padding(5.dp)
            .padding(horizontal = 10.dp)

        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painter,
                tint = PrimaryColor,
                contentDescription = "icon_background",
                // Ensure the icon fills the available space
            )
            SpacerWidth(width = 15)
            Text(
                text = prayerName,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = time,
            style = MaterialTheme.typography.titleSmall,
            color = PrimaryColor,
            fontWeight = FontWeight.Bold
        )

    }
}


@Composable
fun PrayerTimesCard(
    modifier: Modifier = Modifier,
   prayerTimeDataItem:PrayerTimesData?
) {

    Box(
        modifier = modifier
            .background(Color.White)
            .border(width = 1.dp, color = PrimaryColor, RoundedCornerShape(30.dp))
        ,
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.prayer_logo),
                contentDescription ="prayer_logo",
                modifier = Modifier.fillMaxWidth().size(80.dp)
            )

            SpacerHeight(height = 15)

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = prayerTimeDataItem?.date.toString(),
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )

            SpacerHeight(height = 10)


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                PrayerTimeItem(prayerName = "Fajr",
                    painter = painterResource(id = R.drawable.shubuh_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.fajr?:"")
                )
                SpacerHeight(height = 10)

                PrayerTimeItem(prayerName = "Sunrise",
                    painter = painterResource(id = R.drawable.dhuha_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.sunrise?:"")
                )
                SpacerHeight(height = 10)

                PrayerTimeItem(prayerName = "Duhr",
                    painter = painterResource(id = R.drawable.zhuhur_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.duhr?:"")
                )
                SpacerHeight(height = 10)

                PrayerTimeItem(prayerName = "Asr",
                    painter = painterResource(id = R.drawable.asr_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.asr?:"")
                )
                SpacerHeight(height = 10)

                PrayerTimeItem(prayerName = "Maghrib",
                    painter = painterResource(id = R.drawable.maghrib_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.maghrib?:"")
                )
            //    16:07 (EEST)
                SpacerHeight(height = 10)

                PrayerTimeItem(prayerName = "Isha",
                    painter = painterResource(id = R.drawable.isha_icon),
                    time = convertTo12HourFormat(prayerTimeDataItem?.timings?.isha?:"")
                )
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevPrayerTime(modifier: Modifier = Modifier) {
    PrayerTimeItem(prayerName = "Asr", painter = painterResource(id = (R.drawable.asr_icon)), time ="4:00 PM" )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevPrayerTimeCard(modifier: Modifier = Modifier) {
  //  PrayerTimesCard(modifier=Modifier)
}