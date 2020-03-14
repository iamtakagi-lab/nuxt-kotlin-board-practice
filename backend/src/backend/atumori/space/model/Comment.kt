package backend.atumori.space.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mongodb.BasicDBList
import org.bson.Document
import org.omg.CORBA.COMM_FAILURE

data class Comment(
    val id: Long,
    val username: String,
    val context: String,
    val timestamp: Long,
    var removed: Boolean,
    var removedAt: Long,
    var ip: String
) {
    fun toJsonObject() : JsonObject {
        val json = JsonObject()
        json.addProperty("id", id)
        json.addProperty("username", username)
        json.addProperty("context", context)
        json.addProperty("timestamp", timestamp)
        json.addProperty("removed", removed)
        json.addProperty("removedAt", removedAt)
        json.addProperty("ip", ip)
        return json
    }
}

data class NewComment(
    val username: String,
    val context: String,
    var ip: String
)