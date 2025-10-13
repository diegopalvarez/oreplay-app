package org.oreplay.app.viewmodel.home

import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.util.onError
import org.oreplay.app.model.util.onSuccess
import org.oreplay.app.view.home.searchbar.UIFilterState
import kotlin.time.Clock

@OptIn(FlowPreview::class)
class HomeScreenComponent(
    componentContext: ComponentContext,
    private val client: EventClient,
    private val onNavigateToEventScreen: (Event) -> Unit
): ComponentContext by componentContext {

    // Scope
    private val scope = coroutineScope();

    // Navigation to Event Screen
    fun onEvent(event: HomeScreenEvent, raceEvent: Event) {
        when(event) {
            HomeScreenEvent.ClickEvent -> onNavigateToEventScreen(raceEvent)
        }
    }
    // Live Events
    private val _liveEventsList = MutableStateFlow<List<Event>>(emptyList())
    val liveEventsList = _liveEventsList.asStateFlow()

    // Past Events
    private val _pastEventsList = MutableStateFlow<List<Event>>(emptyList())
    val pastEventsList = _pastEventsList.asStateFlow()

    // Future Events
    private val _futureEventsList = MutableStateFlow<List<Event>>(emptyList())
    val futureEventsList = _futureEventsList.asStateFlow()

    fun loadLiveEvents(liveEvents: List<Event>) {
        _liveEventsList.value = (_liveEventsList.value + liveEvents).distinctBy { it.id }.sortedByDescending { it.initialDate }
    }

    fun loadPastEvents(pastEvents: List<Event>) {
        _pastEventsList.value = (_pastEventsList.value + pastEvents).distinctBy { it.id }.sortedByDescending { it.initialDate }
    }

    fun loadFutureEvents(futureEvents: List<Event>) {
        _futureEventsList.value = (_futureEventsList.value + futureEvents).distinctBy { it.id }.sortedByDescending { it.initialDate }
    }

    fun loadEvents(unclassifiedEvents: List<Event>) {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        val live = mutableListOf<Event>()
        val past = mutableListOf<Event>()
        val future = mutableListOf<Event>()

        for(event in unclassifiedEvents) {
            when {
                today < event.initialDate -> future.add(event)
                today > event.finalDate -> past.add(event)
                else -> live.add(event)
            }
        }

        _liveEventsList.value = (_liveEventsList.value + live).distinctBy { it.id }.sortedByDescending { it.initialDate }
        _pastEventsList.value = (_pastEventsList.value + past).distinctBy { it.id }.sortedByDescending { it.initialDate }
        _futureEventsList.value = (_futureEventsList.value + future).distinctBy { it.id }.sortedByDescending { it.initialDate }

    }

    // Filters
    private val _uiFilterState = MutableStateFlow(UIFilterState())
    val uiFilterState: StateFlow<UIFilterState> = _uiFilterState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiFilterState.value = _uiFilterState.value.copy(query = query)
    }

    fun updateSelectedFilters(filters: Set<String>) {
        _uiFilterState.value = _uiFilterState.value.copy(selectedFilters = filters)
    }

    fun updateSelectedDate(date: LocalDate?) {
        _uiFilterState.value = _uiFilterState.value.copy(selectedDate = date)
    }

    // Combined results
    val combinedFilteredEvents: StateFlow<List<Event>> =
        combine(
            liveEventsList,
            pastEventsList,
            futureEventsList,
            uiFilterState,
        ) { live, past, future, filters ->
            (live + past + future).filter { event ->
                val matchesQuery = filters.query.isBlank() || event.description.contains(filters.query, ignoreCase = true)
                val matchesFilter = filters.selectedFilters.isEmpty() || filters.selectedFilters.any { tag ->
                    // You can customize this matching logic:
                    when (tag) {
                        else -> true
                    }
                }
                val matchesDate = filters.selectedDate == null || (filters.selectedDate >= event.initialDate && filters.selectedDate <= event.finalDate)
                matchesQuery && matchesFilter && matchesDate
            }
        }.stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())


    // Debounced API calling

    init {
        setUpEventSearch()
    }

    fun setUpEventSearch(){
        scope.launch {
            uiFilterState
                .map { it.query }
                .debounce(300)
                .filter { query -> query.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    client.getEventsByName(query)
                        .onSuccess { events ->
                            loadEvents(events)
                        }
                        .onError {
                            println("Error searching events: $it")
                        }
                }
        }
    }

    fun fetchLiveEvents() {
        scope.launch {
            client.getTodayEvents()
                .onSuccess { events ->
                    _liveEventsList.value = (_liveEventsList.value + events)
                        .distinctBy { it.id }
                        .sortedByDescending { it.initialDate }
                }
                .onError {
                    println("Error fetching live events: $it")
                }
        }
    }

    fun fetchPastEvents() {
        scope.launch {
            client.getPastEvents()
                .onSuccess { events ->
                    _pastEventsList.value = (_pastEventsList.value + events)
                        .distinctBy { it.id }
                        .sortedByDescending { it.initialDate }
                }
                .onError {
                    println("Error fetching past events: $it")
                }
        }
    }

    fun fetchFutureEvents() {
        scope.launch {
            client.getFutureEvents()
                .onSuccess { events ->
                    _futureEventsList.value = (_futureEventsList.value + events)
                        .distinctBy { it.id }
                        .sortedBy { it.initialDate }
                }
                .onError {
                    println("Error fetching future events: $it")
                }
        }
    }
}