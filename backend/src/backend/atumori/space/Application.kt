package backend.atumori.space

import backend.atumori.space.controller.postController
import backend.atumori.space.controller.reportController
import backend.atumori.space.service.*
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.routing.Routing
import io.ktor.websocket.WebSockets
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.module() {

    val mongoService = MongoService(environment.config)
    val postService = PostService(mongoService)
    val commentService = CommentService(mongoService)
    val discordService = DiscordService(environment.config)
    val reportService = ReportService(mongoService)

    install(DefaultHeaders)
    install(CallLogging)
    install(WebSockets)
    install(Locations)

    install(Routing) {
        postController(mongoService, postService, commentService)
        reportController(reportService, discordService)
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Post)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowCredentials = true
        anyHost()
    }

    install(ContentNegotiation) {
        gson {
            // Configure Gson here
        }
    }

    install(HttpsRedirect) {
        sslPort = 8443

        permanentRedirect = true
    }
}