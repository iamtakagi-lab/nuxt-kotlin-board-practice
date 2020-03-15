package backend.atumori.space.controller

import backend.atumori.space.model.NewPost
import backend.atumori.space.model.NewReport
import backend.atumori.space.service.DiscordService
import backend.atumori.space.service.MongoService
import backend.atumori.space.service.ReportService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Route.reportController(reportService: ReportService, discordService: DiscordService) {

    route("report") {

        post {
            val newreport = call.receive<NewReport>()
            val added = reportService.addReport(newreport)
            discordService.sendReport(added)
            call.response.status(HttpStatusCode.OK)
            call.respond(added)
        }
    }
}