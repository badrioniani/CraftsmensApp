package org.example.project.data.mechanics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.Mechanic

data class MechanicListState(
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val items: List<Mechanic> = emptyList(),
    val error: String? = null,
    // The backend's true total for the current query, regardless of how many
    // pages have been loaded so far.
    val totalCount: Int = 0,
) {
    val canLoadMore: Boolean get() = items.size < totalCount
}

data class MechanicQuery(
    val brand: String? = null,
    val service: String? = null,
    val search: String? = null,
    val city: String? = null,
    val minRating: Double? = null,
    val verified: Boolean? = null,
)

class MechanicListViewModel(
    private val api: MechanicApi = MechanicApi(),
) : ViewModel() {

    private val _state = MutableStateFlow(MechanicListState())
    val state: StateFlow<MechanicListState> = _state.asStateFlow()

    private var query = MechanicQuery()
    private var page = 1
    private var primaryJob: Job? = null
    private var moreJob: Job? = null
    private var allLoaded = false

    private companion object {
        const val PAGE_SIZE = 12
        const val ALL_PAGE_SIZE = 500
    }

    /**
     * Map / Home: load everything in one shot (the map needs every pin and Home
     * only picks a few featured). No incremental paging here.
     */
    fun loadAll() {
        if (allLoaded || _state.value.items.isNotEmpty()) return
        allLoaded = true
        query = MechanicQuery()
        primaryJob?.cancel()
        moreJob?.cancel()
        _state.value = _state.value.copy(loading = true, error = null)
        primaryJob = viewModelScope.launch {
            runCatching { api.list(pageSize = ALL_PAGE_SIZE) }
                .onSuccess { page ->
                    _state.value = MechanicListState(
                        items = page.results.map { it.toUiModel() },
                        totalCount = page.count,
                    )
                }
                .onFailure {
                    _state.value = MechanicListState(error = it.message ?: "Could not load mechanics")
                }
        }
    }

    /** List screen: (re)load the first page for [newQuery]. */
    fun apply(newQuery: MechanicQuery) {
        if (newQuery == query && _state.value.items.isNotEmpty()) return
        query = newQuery
        page = 1
        allLoaded = false
        primaryJob?.cancel()
        moreJob?.cancel()
        _state.value = _state.value.copy(loading = true, loadingMore = false, error = null)
        primaryJob = viewModelScope.launch {
            runCatching { fetch(1) }
                .onSuccess { res ->
                    _state.value = MechanicListState(
                        items = res.results.map { it.toUiModel() },
                        totalCount = res.count,
                    )
                }
                .onFailure {
                    _state.value = MechanicListState(error = it.message ?: "Could not load mechanics")
                }
        }
    }

    /** Append the next page (infinite scroll). No-op while busy or fully loaded. */
    fun loadMore() {
        val st = _state.value
        if (st.loading || st.loadingMore || !st.canLoadMore) return
        moreJob?.cancel()
        _state.value = st.copy(loadingMore = true)
        val next = page + 1
        moreJob = viewModelScope.launch {
            runCatching { fetch(next) }
                .onSuccess { res ->
                    page = next
                    _state.value = _state.value.copy(
                        items = _state.value.items + res.results.map { it.toUiModel() },
                        totalCount = res.count,
                        loadingMore = false,
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(loadingMore = false)
                }
        }
    }

    private suspend fun fetch(p: Int) = api.list(
        brand = query.brand,
        service = query.service,
        search = query.search,
        city = query.city,
        minRating = query.minRating,
        verified = query.verified,
        page = p,
        pageSize = PAGE_SIZE,
    )
}
