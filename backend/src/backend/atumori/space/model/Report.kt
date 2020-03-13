package backend.atumori.space.model

data class NewReport(val name: String, val context: String)

data class Report(val id: Long, val name: String, val context: String, val timestamp: String, val ip: String)