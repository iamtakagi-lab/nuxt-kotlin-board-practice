package backend.atumori.space.model

import org.bson.Document
import java.text.SimpleDateFormat
import java.util.*

data class NewReport(val type: String, val path: String, val ip: String)

data class Report(val id: Long, val type: String, val path: String, val ip: String, val timestamp: Long) {
    fun toDocument() : Document {
        return Document().apply {
            put("type", type)
            put("path", path)
            put("timestamp", timestamp)
            put("ip", ip)
        }
    }


    fun toDiscordText() : String{
       return "【通報通知】\n" +
               "タイプ: " + type + "\n" +
               "送信元IP: " + ip + "\n" +
               "日付: " + SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date(timestamp)) + "\n" +
               "https://atsumorichannel.net/" + path
    }
}