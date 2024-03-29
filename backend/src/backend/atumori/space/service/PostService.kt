package backend.atumori.space.service

import backend.atumori.space.model.*
import backend.atumori.space.util.JsonUtils
import org.bson.Document
import java.util.*

/**
 * 投稿管理サービス
 */
class PostService(val mongoService: MongoService){

    /**
     * Utils
     */
    companion object {

        /**
         * DocumentからPostにして返す
         */
        fun toPost(document: Document): Post {

            document.apply {
                val comments = arrayListOf<Comment>()

                val docClazz: Class<out MutableList<*>?> = ArrayList<String>().javaClass
                val array = get("comments", docClazz)!!

                for (str in array) {
                    val json = JsonUtils.JSON_PARSER.parse(str.toString()).asJsonObject
                    comments.add(
                        Comment(
                            json.get("id").asLong,
                            json.get("username").asString,
                            json.get("context").asString,
                            json.get("timestamp").asLong,
                            json.get("removed").asBoolean,
                            json.get("removedAt").asLong,
                            json.get("ip").asString
                        )
                    )
                }


                return Post(
                    getLong("id"),
                    getString("type"),
                    getString("username"),
                    getString("fc"),
                    getString("title"),
                    getString("body"),
                    getString("tags"),
                    getString("password"),
                    getLong("publishedAt"),
                    getLong("lastEdited"),
                    getLong("lastBumped"),
                    comments,
                    getBoolean("closed"),
                    getLong("closedAt"),
                    getString("ip")
                )
            }
        }
    }

    /**
     * 投稿を追加
     */
    fun addPost(newpost: NewPost) : Post {
        val post = Post(
            mongoService.posts.countDocuments() + 1,
            newpost.type,
            newpost.username,
            newpost.fc,
            newpost.title,
            newpost.body,
            newpost.tags,
            newpost.password,
            Date().time,
            0,
            0,
            arrayListOf(),
            false,
            0,
            newpost.ip)
        mongoService.replacePost(post.id, post.toDocument())
        return post
    }

    /**
     * 投稿を編集
     */
    fun editPost(editPost: EditPost) : Boolean {
        val doc = mongoService.findPostById(editPost.id) ?: return false
        val post = toPost(doc)
        post.lastEdited = Date().time
        post.username = editPost.username
        post.fc = editPost.fc
        post.title = editPost.title
        post.body = editPost.body
        post.tags = editPost.tags
        post.ip = editPost.ip
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }

    /**
     * 投稿を締め切る
     */
    fun closePost(id: Long) : Boolean {
        val doc = mongoService.findPostById(id) ?: return false
        val post = toPost(doc)
        post.closed = true
        post.closedAt = Date().time
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }

    /**
     * 投稿の表示順位を上げる
     */
    fun bumpPost(id: Long) : Boolean {
        val doc = mongoService.findPostById(id) ?: return false
        val post = toPost(doc)
        post.lastBumped = Date().time
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }
}