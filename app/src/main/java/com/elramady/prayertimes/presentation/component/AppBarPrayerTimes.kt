package com.elramady.prayertimes.presentation.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elramady.prayertimes.R
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor


@Composable
fun AppBarPrayerTimes(
    modifier: Modifier = Modifier,
    day:String="",
    location:String="",
    onClickNextPrayer:()->Unit,
    onClickPreviousPrayer:()->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        IconWithBackground(painter = painterResource(id = R.drawable.previous),  showRippleEffect = false,sizeBackground = 40, tint = PrimaryColor){
            onClickPreviousPrayer.invoke()
        }

        SpacerWidth(width = 5)

        Column(
            modifier = Modifier.fillMaxWidth(.7f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            SpacerHeight(height = 5)

            Text(
                text = location,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

        }

        SpacerWidth(width = 20)
        IconWithBackground(painter = painterResource(id = R.drawable.next),showRippleEffect = false,sizeBackground = 40, tint = PrimaryColor){
            onClickNextPrayer.invoke()
        }

    }
}

