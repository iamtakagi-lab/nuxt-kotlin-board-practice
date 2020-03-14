package backend.atumori.space.controller

import backend.atumori.space.service.MongoService
import backend.atumori.space.service.ReportService
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.postRouter(mongoService: MongoService, reportService: ReportService) {

    post {

    }
}