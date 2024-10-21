package com.elramady.prayertimes.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerWidth(modifier: Modifier = Modifier, width:Int) {
    Spacer(modifier = modifier.width(width.dp))
}

@Composable
fun SpacerHeight(modifier: Modifier = Modifier,height:Int) {
    Spacer(modifier = modifier.height(height.dp))
}