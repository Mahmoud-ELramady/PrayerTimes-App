package com.elramady.prayertimes.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconWithBackground(
    modifier: Modifier = Modifier,
    painter: Painter,
    sizeBackground: Int,
    shape: Shape = RoundedCornerShape(12.dp),
    tint: Color =Color.Unspecified,
    elevation: Dp = 3.dp, // Specify elevation here
    background:Color=Color.White,
    paddingIcon:Int=12,
    showRippleEffect:Boolean=true,
    modifierImage: Modifier=Modifier,
    onClick:()->Unit,
) {
    Surface(
        shape = shape,
        color = background,
       onClick = {
           onClick.invoke()
                 },
        shadowElevation = elevation,
        modifier = modifier
            .size(sizeBackground.dp)
//            .clickable(
//
//            )  {  }

    ) {
        Icon(
            modifier = modifierImage.padding(paddingIcon.dp),
            painter = painter,
            tint = tint,
            contentDescription = "icon_background",
            // Ensure the icon fills the available space
        )
    }
}