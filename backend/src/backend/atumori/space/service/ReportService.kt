package backend.atumori.space.service

import backend.atumori.space.model.NewReport
import backend.atumori.space.model.Report
import java.util.*

/**
 * 通報管理サービス
 */
class ReportService(val mongoService: MongoService) {

    fun addReport(newReport: NewReport) : Report {
        val report = Report(mongoService.reports.countDocuments() + 1, newReport.type, newReport.path, newReport.ip, Date().time)
        mongoService.replaceReport(report.id, report.toDocument())
        return report
    }
}