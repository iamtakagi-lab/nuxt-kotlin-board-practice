package backend.atumori.space.service

import backend.atumori.space.model.Comment
import backend.atumori.space.model.NewComment
import backend.atumori.space.model.Post
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mongodb.BasicDBList
import io.ktor.server.engine.commandLineEnvironment
import java.lang.Integer.parseInt
import java.util.*
import java.util.function.Consumer

class CommentService (val mongoService: MongoService){

    /**
     * コメントを追加
     */
    fun addComment(postId: Long, newComment: NewComment) : Boolean {
        val doc = mongoService.findPostById(postId) ?: return false
        val post = PostService.toPost(doc)
        val comment = Comment(post.comments.size.toLong()+1, newComment.username, newComment.context, Date().time, false, 0, newComment.ip)
        post.comments.add(comment)
        mongoService.replacePost(postId, post.toDocument())
        return true
    }


    /**
     * コメント
     */
    fun getComments(postId: Long) : JsonArray? {
        val doc = mongoService.findPostById(postId) ?: return null
        val post = PostService.toPost(doc)
        val array = JsonArray()

        //コメント & 返信用の空配列をぶちこんでおく
        post.comments.forEach { comment ->
            val json = comment.toJsonObject()
            json.add("replies", JsonArray())
            array.add(json)
        }

        //返信を取得してぶちこむ
        post.comments.forEach { comment ->
            comment.context.apply {
                if(contains(">>")) {
                    val replyTo = split(">")[2].replace(("[^\\d]").toRegex(), "").toInt()
                    if (array.size() >= replyTo) {
                        array.get(replyTo - 1).asJsonObject.get("replies").asJsonArray.add(comment.toJsonObject())
                    }
                }
            }
        }
        return array
    }
}