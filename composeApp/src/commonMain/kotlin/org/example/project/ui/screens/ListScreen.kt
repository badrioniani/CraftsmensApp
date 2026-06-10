package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.Mechanic
import org.example.project.data.catalog.CarBrandDto
import org.example.project.data.catalog.CatalogViewModel
import org.example.project.data.catalog.CityDto
import org.example.project.data.catalog.ServiceTypeDto
import org.example.project.data.mechanics.MechanicListViewModel
import org.example.project.data.mechanics.MechanicQuery
import org.example.project.ui.components.AppButton
import org.example.project.ui.components.AppSpinner
import org.example.project.ui.components.ButtonVariant
import org.example.project.ui.components.IconButton40
import org.example.project.ui.components.MechanicCardSkeleton
import org.example.project.ui.components.MechanicListCard
import org.example.project.ui.components.Pill
import org.example.project.ui.components.ScreenHeader
import org.example.project.ui.i18n.LocalSetInputFocused
import org.example.project.ui.i18n.LocalStrings
import org.example.project.ui.icons.IconFilter
import org.example.project.ui.icons.IconMap
import org.example.project.ui.icons.IconSearch
import org.example.project.ui.icons.IconX
import org.example.project.ui.map.rememberCurrentLocation
import org.example.project.ui.theme.CraftsmenColors
import org.example.project.ui.util.haversineKm

enum class SortMode { Distance, Rating }

@Composable
fun MechanicsListScreen(
    theme: CraftsmenColors,
    initialQuery: String,
    onBack: () -> Unit,
    onPickMech: (Mechanic) -> Unit,
    onMap: () -> Unit,
) {
    val s = LocalStrings.current
    var search by remember { mutableStateOf(initialQuery) }
    var brand by remember { mutableStateOf<String?>(null) }
    var service by remember { mutableStateOf<String?>(null) }
    var city by remember { mutableStateOf<String?>(null) }
    var minRating by remember { mutableStateOf(0.0) }
    var verifiedOnly by remember { mutableStateOf(false) }
    var sort by remember { mutableStateOf(SortMode.Rating) }
    var filterOpen by remember { mutableStateOf(false) }

    val vm: MechanicListViewModel = viewModel { MechanicListViewModel() }
    val listState by vm.state.collectAsStateWithLifecycle()
    val catalogVm: CatalogViewModel = viewModel { CatalogViewModel() }
    val catalog by catalogVm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { catalogVm.load() }
    LaunchedEffect(brand, service, city, minRating, verifiedOnly, search) {
        vm.apply(
            MechanicQuery(
                brand = brand,
                service = service,
                search = search.ifBlank { null },
                city = city,
                minRating = minRating.takeIf { it > 0 },
                verified = verifiedOnly.takeIf { it },
            )
        )
    }

    // Best-effort device location. Null when permission isn't granted or the
    // platform can't resolve a fix yet — in that case `distance` stays unknown
    // and the Nearest sort falls back to the original order.
    val myLocation = rememberCurrentLocation()

    val itemsWithDistance = remember(listState.items, myLocation) {
        if (myLocation == null) listState.items
        else listState.items.map { m ->
            val d = if (m.lat != null && m.lng != null) {
                haversineKm(myLocation.lat, myLocation.lng, m.lat, m.lng)
            } else Double.POSITIVE_INFINITY
            m.copy(distance = d)
        }
    }

    // Nearest is a pure-distance sort: tier boosts are skipped so the closest
    // mechanic always wins regardless of VIP status. Rating keeps the site's
    // default SUPER VIP → VIP → others pinning.
    val sorted = when (sort) {
        SortMode.Distance -> itemsWithDistance.sortedBy { it.distance }
        SortMode.Rating -> itemsWithDistance.sortedWith(
            compareByDescending<Mechanic> { it.superVip }
                .thenByDescending { it.vip }
                .thenByDescending { it.rating }
        )
    }

    // Infinite scroll: load the next page as the user nears the bottom.
    val resultsListState = rememberLazyListState()
    // Snap back to the top whenever the sort mode flips, so the user sees the
    // new first item instead of being stranded mid-list at the same offset.
    LaunchedEffect(sort) { resultsListState.animateScrollToItem(0) }
    val reachedEnd by remember {
        derivedStateOf {
            val last = resultsListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            last >= sorted.size - 3
        }
    }
    LaunchedEffect(reachedEnd, listState.canLoadMore, listState.loadingMore) {
        if (reachedEnd && listState.canLoadMore && !listState.loadingMore) vm.loadMore()
    }

    // True total from the server (not just what's loaded so far).
    val displayCount = if (listState.totalCount > 0) listState.totalCount else sorted.size

    Box(modifier = Modifier.fillMaxSize().background(theme.bg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(
                theme = theme,
                onBack = onBack,
                title = s.catalogTitle,
                subtitle = "$displayCount ${s.catalogSubtitle}",
                right = {
                    IconButton40(theme = theme, onClick = onMap) {
                        IconMap(size = 18.dp, color = theme.text)
                    }
                },
            )

            // Search bar
            SearchField(theme, search, onChange = { search = it })

            // Filter / sort chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(theme.bgRaised)
                            .border(1.dp, theme.border, CircleShape)
                            .clickable { filterOpen = true }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        IconFilter(size = 13.dp, color = theme.text)
                        Text(s.filters, color = theme.text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        val hasFilters = brand != null || service != null || city != null || minRating > 0 || verifiedOnly
                        if (hasFilters) {
                            Box(
                                modifier = Modifier.size(6.dp).clip(CircleShape).background(theme.accent),
                            )
                        }
                    }
                }
                items(SortMode.entries.toList()) { mode ->
                    Pill(
                        theme = theme,
                        text = when (mode) {
                            SortMode.Distance -> s.sortNearest
                            SortMode.Rating -> s.sortRating
                        },
                        active = sort == mode,
                        onClick = { sort = mode },
                        small = true,
                    )
                }
            }

            if (listState.loading && sorted.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(4) { MechanicCardSkeleton(theme) }
                }
            } else if (sorted.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(s.catalogEmptyTitle, color = theme.text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(s.catalogEmptyBody, color = theme.textDim, fontSize = 13.sp)
                    Spacer(Modifier.height(14.dp))
                    Text(
                        s.filtersReset,
                        color = theme.accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            brand = null; service = null; city = null; minRating = 0.0; verifiedOnly = false; search = ""
                        },
                    )
                }
            } else {
                LazyColumn(
                    state = resultsListState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(sorted, key = { it.id }) { m -> MechanicListCard(theme, m) { onPickMech(m) } }
                    if (listState.loadingMore) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center,
                            ) { AppSpinner(size = 22.dp, color = theme.accent) }
                        }
                    }
                }
            }
        }

        if (filterOpen) {
            FilterSheet(
                theme = theme,
                brand = brand,
                service = service,
                city = city,
                minRating = minRating,
                verifiedOnly = verifiedOnly,
                brands = catalog.brands,
                services = catalog.services,
                cities = catalog.cities,
                onApply = { newBrand, newService, newCity, newRating, newVerified ->
                    brand = newBrand
                    service = newService
                    city = newCity
                    minRating = newRating
                    verifiedOnly = newVerified
                    filterOpen = false
                },
                onClose = { filterOpen = false },
            )
        }
    }
}

@Composable
private fun SearchField(theme: CraftsmenColors, value: String, onChange: (String) -> Unit) {
    val s = LocalStrings.current
    val setInputFocused = LocalSetInputFocused.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(theme.bgInput)
            .border(1.dp, theme.border, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        IconSearch(size = 16.dp, color = theme.textDim)
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty()) {
                Text(s.searchPlaceholder, color = theme.textDim, fontSize = 14.sp, maxLines = 1)
            }
            BasicTextField(
                value = value,
                onValueChange = onChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { setInputFocused(it.isFocused) },
                singleLine = true,
                textStyle = TextStyle(color = theme.text, fontSize = 14.sp),
                cursorBrush = SolidColor(theme.accent),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }),
            )
        }
        if (value.isNotEmpty()) {
            Box(modifier = Modifier.clickable { onChange("") }) {
                IconX(size = 14.dp, color = theme.textDim)
            }
        }
    }
}

@Composable
private fun FilterSheet(
    theme: CraftsmenColors,
    brand: String?,
    service: String?,
    city: String?,
    minRating: Double,
    verifiedOnly: Boolean,
    brands: List<CarBrandDto>,
    services: List<ServiceTypeDto>,
    cities: List<CityDto>,
    onApply: (brand: String?, service: String?, city: String?, minRating: Double, verifiedOnly: Boolean) -> Unit,
    onClose: () -> Unit,
) {
    val s = LocalStrings.current
    var b by remember { mutableStateOf(brand) }
    var sv by remember { mutableStateOf(service) }
    var c by remember { mutableStateOf(city) }
    var r by remember { mutableStateOf(minRating) }
    var v by remember { mutableStateOf(verifiedOnly) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // Sheet caps at 90% of the screen: handle + title stay fixed at the top,
        // the action buttons stay pinned at the bottom, and only the filter
        // sections scroll in between — so nothing ever runs off-screen.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight * 0.9f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(theme.bgRaised)
                .clickable(enabled = false, onClick = {}),
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(theme.borderStrong)
                    .align(Alignment.CenterHorizontally),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(s.filters, color = theme.text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Box(modifier = Modifier.clickable { onClose() }.padding(4.dp)) {
                    IconX(size = 20.dp, color = theme.textDim)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
            ) {
            // Brand
            FilterSection(theme, s.garageBrand) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    item {
                        Pill(theme, s.anyBrand, active = b == null, onClick = { b = null }, small = true)
                    }
                    items(brands) { cb ->
                        Pill(theme, cb.name, active = b == cb.name, onClick = { b = cb.name }, small = true)
                    }
                }
            }
            // Service
            FilterSection(theme, s.specialties) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    item {
                        Pill(theme, s.anyService, active = sv == null, onClick = { sv = null }, small = true)
                    }
                    items(services) { sp ->
                        Pill(theme, sp.name, active = sv == sp.name, onClick = { sv = sp.name }, small = true)
                    }
                }
            }
            // City
            FilterSection(theme, s.cityLabel) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    item {
                        Pill(theme, s.anyCity, active = c == null, onClick = { c = null }, small = true)
                    }
                    items(cities) { ct ->
                        Pill(theme, ct.name, active = c == ct.name, onClick = { c = ct.name }, small = true)
                    }
                }
            }
            // Min rating
            FilterSection(theme, s.statRating) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(0.0, 4.0, 4.5, 4.8).forEach { rv ->
                        Pill(
                            theme,
                            if (rv == 0.0) s.anyRating else "$rv${s.ratingStars}",
                            active = r == rv,
                            onClick = { r = rv },
                            small = true,
                        )
                    }
                }
            }
            // Verified only
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(s.verifiedOnly, color = theme.text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                ToggleSwitch(on = v, accent = theme.accent, track = theme.border, onToggle = { v = !v })
            }
            } // end scrollable sections

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                AppButton(
                    theme = theme,
                    text = s.filtersReset,
                    onClick = { b = null; sv = null; c = null; r = 0.0; v = false },
                    variant = ButtonVariant.Secondary,
                    modifier = Modifier.weight(1f),
                )
                AppButton(
                    theme = theme,
                    text = s.applyFilters,
                    onClick = { onApply(b, sv, c, r, v) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun FilterSection(theme: CraftsmenColors, label: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(label, color = theme.text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        content()
        Spacer(Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(theme.border))
    }
}

@Composable
private fun ToggleSwitch(on: Boolean, accent: Color, track: Color, onToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 50.dp, height = 28.dp)
            .clip(CircleShape)
            .background(if (on) accent else track)
            .clickable(onClick = onToggle),
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(if (on) Alignment.CenterEnd else Alignment.CenterStart),
        )
    }
}
