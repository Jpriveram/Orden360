package edu.ucb.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val OrdenNavy = Color(0xFF0B192C)
internal val OrdenBlue = Color(0xFF1E3E62)
internal val OrdenGold = Color(0xFFD4AF37)
internal val OrdenBackground = Color(0xFFF8FAFC)
internal val OrdenSlate = Color(0xFF475569)

@Composable
internal fun OrdenWordmark(color: Color, size: TextUnit, weight: FontWeight) {
    Text(
        text = buildAnnotatedString {
            append("Orden")
            withStyle(SpanStyle(color = OrdenGold)) { append("360") }
        },
        color = color,
        fontSize = size,
        fontWeight = weight,
    )
}

@Composable
internal fun WelcomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(OrdenNavy),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val shape = RoundedCornerShape(24.dp)
            Box(
                modifier = Modifier.size(96.dp)
                    .background(OrdenBlue, shape)
                    .border(3.dp, OrdenGold, shape),
                contentAlignment = Alignment.Center,
            ) {
                Image(painterResource(R.drawable.orden_house_splash), null)
            }
            Spacer(Modifier.height(20.dp))
            OrdenWordmark(Color.White, 36.sp, FontWeight.Black)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "TU TERRENO IDEAL EN COCHABAMBA",
                color = Color(0xFFF1D27E),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(Modifier.weight(1.15f))
        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(Modifier.size(40.dp, 4.dp).background(OrdenGold, RoundedCornerShape(2.dp)))
            Text("Cochabamba, Bolivia", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 402, heightDp = 810)
@Composable
private fun WelcomePreview() {
    WelcomeScreen()
}
