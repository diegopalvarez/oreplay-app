package org.oreplay.app.model

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import org.oreplay.app.model.util.NetworkError
import org.oreplay.app.model.util.Result

class EventClient (private val httpClient: HttpClient) {
    val baseURL = "https://www.oreplay.es/api/v1/events"
    suspend fun getEvents(url: String) : Result<List<Event>, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = url
            )
        }
        catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception) {
            println("Unknown exception: $e")
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when(response.status.value) {
            200 -> {
                val eventList: EventResponse = response.body()
                return Result.Success(eventList.data)
            }
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getTodayEvents() : Result<List<Event>, NetworkError> {
        var todayUrl = baseURL + "?when=today"

        return getEvents(todayUrl)
    }

    suspend fun getPastEvents() : Result<List<Event>, NetworkError> {
        var todayUrl = baseURL + "?when=past"

        return getEvents(todayUrl)
    }

    suspend fun getFutureEvents() : Result<List<Event>, NetworkError> {
        var todayUrl = baseURL + "?when=future"

        return getEvents(todayUrl)
    }

    suspend fun getEventDetails(event: Event) : Result<EventDetails, NetworkError> {
        println(event.links.self)
        val response = try {
            httpClient.get(
                urlString = event.links.self
            )
        }
        catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception) {
            println("Unknown exception: $e")
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when(response.status.value) {
            200 -> {
                println(response.bodyAsText())
                val eventDetails: EventDetailResponse= response.body()
                return Result.Success(eventDetails.data)
            }
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getClasses(stage: Stage) : Result<List<Class>, NetworkError> {
        println("Getting classes" + stage.links.classes)
        val response = try {
            httpClient.get(
                urlString = stage.links.classes
            )
        }
        catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception) {
            println("Unknown exception: $e")
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when(response.status.value) {
            200 -> {
                println(response.bodyAsText())
                val stageClasses: ClassResponse= response.body()
                return Result.Success(stageClasses.data)
            }
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getResults(stage: Stage, raceClass: Class) : Result<List<RunnerResult>, NetworkError> {
        val url = stage.links.results.dropLast(1) + "?class_id=" + raceClass.id + "&forceSameDay=true"
        println("Getting results: " + url)
        val response = try {
            httpClient.get(
                // TODO - Check forceSameDay ???
                // TODO - Assuming they keep their link style, but WHO KNOWS
                urlString = stage.links.results.dropLast(1) + "?class_id=" + raceClass.id + "&forceSameDay=true"
            )
        }
        catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception) {
            println("Unknown exception: $e")
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when(response.status.value) {
            200 -> {
                println(response.bodyAsText())
                val classResults: ResultsResponse = response.body()
                return Result.Success(classResults.data)
            }
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}