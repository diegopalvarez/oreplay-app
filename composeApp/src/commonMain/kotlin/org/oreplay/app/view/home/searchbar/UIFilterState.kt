package org.oreplay.app.view.home.searchbar

import kotlinx.datetime.LocalDate

data class UIFilterState(
    val query: String = "",
    val selectedFilters: Set<String> = emptySet(),
    val selectedDate: LocalDate? = null
)