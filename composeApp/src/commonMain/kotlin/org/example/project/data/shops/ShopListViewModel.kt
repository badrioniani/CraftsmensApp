package org.example.project.data.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.Shop

data class ShopListState(
    val loading: Boolean = false,
    val items: List<Shop> = emptyList(),
    val error: String? = null,
    val totalCount: Int = 0,
)

class ShopListViewModel(
    private val api: ShopApi = ShopApi(),
) : ViewModel() {

    private val _state = MutableStateFlow(ShopListState())
    val state: StateFlow<ShopListState> = _state.asStateFlow()

    private var inFlight: Job? = null
    private var lastCategory: String? = "__never__"

    /** [category] is the backend category id, or null for all shops. */
    fun load(category: String? = null) {
        if (lastCategory == category && _state.value.items.isNotEmpty()) return
        lastCategory = category
        inFlight?.cancel()
        _state.value = _state.value.copy(loading = true, error = null)
        inFlight = viewModelScope.launch {
            runCatching { api.list(category = category, pageSize = 500) }
                .onSuccess { page ->
                    _state.value = ShopListState(
                        loading = false,
                        items = page.results.map { it.toUiModel() },
                        totalCount = page.count,
                    )
                }
                .onFailure {
                    _state.value = ShopListState(
                        loading = false,
                        error = it.message ?: "Could not load shops",
                    )
                }
        }
    }
}
