package backend.atumori.space.model

import backend.atumori.space.service.PostService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mongodb.BasicDBList
import com.mongodb.util.JSON
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.routing.Route
import io.ktor.routing.get
import org.bson.Document


@Location("/posts/{id}")
/**
 * @param id 可変
 * @param type 不変
 * @param username 可変
 * @param fc 可変
 * @param title 可変
 * @param body 可変
 * @param tags 可変
 * @param password 不変
 * @param publishedAt 不変
 * @param lastEdited 可変
 * @param lastBumped 可変
 * @param comments 可変
 * @param deleted 可変
 */
data class Post(
    var id: Long,
    var type: String,
    var username: String,
    var fc: String,
    var title: String,
    var body: String,
    var tags: String,
    var password: String,
    var publishedAt: Long,
    var lastEdited: Long,
    var lastBumped: Long,
    var comments: MutableList<Comment>,
    var closed: Boolean,
    var closedAt: Long,
    var ip: String
) {
    fun toDocument(): Document {
        val doc = Document()

        doc.put("id", id)
        doc.put("type", type)
        doc.put("username", username)
        doc.put("fc", fc)
        doc.put("title", title)
        doc.put("body", body)
        doc.put("tags", tags)
        doc.put("password", password)
        doc.put("publishedAt", publishedAt)
        doc.put("lastEdited", lastEdited)
        doc.put("lastBumped", lastBumped)
        doc.put("closedAt", closedAt)

        val commentsArray = BasicDBList()

        comments.forEach { comment ->
            commentsArray.add(comment.toJsonObject().toString())
        }

        doc.put("comments", commentsArray)
        doc.put("closed", closed)
        doc.put("ip", ip)

        return doc
    }
}

data class EditPost(
    var id: Long,
    val username: String,
    var fc: String,
    var title: String,
    var body: String,
    var tags: String,
    var ip: String
)

data class NewPost(
    val type: String,
    val username: String,
    var fc: String,
    var title: String,
    var body: String,
    var tags: String,
    val password: String,
    var ip: String
)