package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.Mechanic
import org.example.project.data.mechanics.MechanicListViewModel
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconBack
import org.example.project.ui.icons.IconChevron
import org.example.project.ui.icons.IconStar
import org.example.project.ui.map.MapCamera
import org.example.project.ui.map.MapPin
import org.example.project.ui.map.MapView
import org.example.project.ui.map.rememberCurrentLocation
import org.example.project.ui.theme.CraftsmenColors

// Fallback center: Tbilisi
private val TbilisiCamera = MapCamera(lat = 41.7151, lng = 44.8271, zoom = 12f)

@Composable
fun MapScreen(
    theme: CraftsmenColors,
    onBack: () -> Unit,
    onPickMech: (Mechanic) -> Unit,
) {
    val s = LocalStrings.current
    val vm: MechanicListViewModel = viewModel { MechanicListViewModel() }
    val listState by vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { vm.loadAll() }

    val mechs = listState.items.filter { it.lat != null && it.lng != null }
    var activeId by remember { mutableStateOf<String?>(null) }
    val active = mechs.firstOrNull { it.id == activeId } ?: mechs.firstOrNull()

    val userCamera = rememberCurrentLocation()
    val camera = userCamera ?: TbilisiCamera

    val pins = remember(mechs) {
        mechs.map { m ->
            MapPin(
                id = m.id,
                lat = m.lat!!,
                lng = m.lng!!,
                title = m.shop,
                subtitle = "${m.rating} ★ · ${m.district}, ${m.city}",
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                pins = pins,
                camera = camera,
                selectedPinId = activeId,
                showUserLocation = true,
                onPinClick = { id -> activeId = id },
            )

            // Top bar overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center,
                ) { IconBack(size = 20.dp, color = theme.text) }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(theme.bgRaised)
                        .border(1.dp, theme.border, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = s.mapViewMap,
                            color = theme.text,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "${mechs.size} ${s.nearby}",
                            color = theme.textMute,
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }

        // Bottom card for the currently active pin
        active?.let { m ->
            Column(modifier = Modifier.fillMaxWidth().background(theme.bg)) {
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 14.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(theme.bgCard)
                        .border(1.dp, theme.border, RoundedCornerShape(16.dp))
                        .clickable { onPickMech(m) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(m.photo),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = m.initials,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = m.shop, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = m.name, color = theme.textDim, fontSize = 12.sp)
                        Spacer(Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                IconStar(size = 11.dp, color = theme.accent)
                                Text(
                                    text = m.rating.toString(),
                                    color = theme.text,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Text(
                                text = "${m.district}, ${m.city}",
                                color = theme.textDim,
                                fontSize = 11.sp,
                            )
                        }
                    }
                    IconChevron(size = 18.dp, color = theme.textDim)
                }
            }
        }
    }
}
