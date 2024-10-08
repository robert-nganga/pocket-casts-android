package au.com.shiftyjelly.pocketcasts.servers.bumpstats

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WpComService {

    @POST("/rest/v1.1/tracks/record")
    suspend fun bumpStatAnonymously(@Body request: AnonymousBumpStatsRequest): Response<String>
}
