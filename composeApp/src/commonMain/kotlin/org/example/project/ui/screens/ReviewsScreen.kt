package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Mechanic
import org.example.project.data.REVIEWS
import org.example.project.data.SYNTHETIC_REVIEWS
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.components.Stars
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ReviewsScreen(theme: CraftsmenColors, mech: Mechanic, onBack: () -> Unit) {
    val s = LocalStrings.current
    val reviews = REVIEWS.filter { it.mechId == mech.id } + SYNTHETIC_REVIEWS

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        ScreenHeader(
            theme = theme,
            title = s.reviewsTitle,
            subtitle = "${mech.name.uppercase()} · ${mech.reviews} ${s.reviewsSuffix}",
            onBack = onBack,
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
        ) {
            item { RatingSummary(theme, mech) }
            item { Spacer(Modifier.height(8.dp)) }
            items(reviews) { r ->
                ReviewCard(theme, r)
                Spacer(Modifier.height(8.dp))
            }
            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun RatingSummary(theme: CraftsmenColors, mech: Mechanic) {
    val s = LocalStrings.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column {
            Text(
                text = mech.rating.toString(),
                color = theme.text,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 48.sp,
            )
            Stars(rating = mech.rating, size = 14.dp, theme = theme)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "${mech.reviews} ${s.reviewsSuffix}",
                color = theme.textMute,
                fontSize = 11.sp,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            listOf(5 to 78, 4 to 16, 3 to 4, 2 to 1, 1 to 1).forEach { (s, pct) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = s.toString(), color = theme.textDim, fontSize = 11.sp, modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(theme.border),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(pct / 100f)
                                .height(4.dp)
                                .background(theme.accent),
                        )
                    }
                    Text(
                        text = "$pct%",
                        color = theme.textMute,
                        fontSize = 10.sp,
                        modifier = Modifier.width(30.dp),
                    )
                }
            }
        }
    }
}
