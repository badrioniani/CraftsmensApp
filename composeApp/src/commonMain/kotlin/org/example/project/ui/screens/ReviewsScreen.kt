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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Mechanic
import org.example.project.data.Review
import org.example.project.data.mechanics.MechanicApi
import org.example.project.data.mechanics.toUiModel
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.components.Stars
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.theme.CraftsmenColors

@Composable
fun ReviewsScreen(theme: CraftsmenColors, mech: Mechanic, onBack: () -> Unit) {
    val s = LocalStrings.current
    val reviewApi = remember { MechanicApi() }
    val reviews by produceState(initialValue = emptyList<Review>(), mech.id) {
        val id = mech.id.toIntOrNull()
        value = if (id != null) {
            runCatching { reviewApi.reviews(id).results.map { it.toUiModel(mech.id) } }.getOrDefault(emptyList())
        } else emptyList()
    }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        ScreenHeader(
            theme = theme,
            title = s.reviewsTitle,
            subtitle = "${mech.name.uppercase()} · ${mech.reviews} ${s.detailReviewsCount}",
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
                text = "${mech.reviews} ${s.detailReviewsCount}",
                color = theme.textMute,
                fontSize = 11.sp,
            )
        }
    }
}
