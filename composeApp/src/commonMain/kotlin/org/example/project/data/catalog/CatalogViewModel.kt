package org.example.project.data.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CatalogState(
    val brands: List<CarBrandDto> = emptyList(),
    val services: List<ServiceTypeDto> = emptyList(),
    val cities: List<CityDto> = emptyList(),
)

/** Loads the static catalog (brands / services / cities) used by filter UIs. */
class CatalogViewModel(
    private val api: CatalogApi = CatalogApi(),
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    private var loaded = false

    fun load() {
        if (loaded) return
        loaded = true
        viewModelScope.launch {
            val brands = runCatching { api.brands() }.getOrDefault(emptyList())
            val services = runCatching { api.services() }.getOrDefault(emptyList())
            val cities = runCatching { api.cities() }.getOrDefault(emptyList())
            _state.value = CatalogState(brands = brands, services = services, cities = cities)
        }
    }
}
