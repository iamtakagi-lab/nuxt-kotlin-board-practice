package backend.atumori.space.service

import backend.atumori.space.model.*
import backend.atumori.space.util.JsonUtil
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bson.Document
import java.util.*


class PostService(val mongoService: MongoService){

    fun toPost(document: Document) : Post {
        document.apply {
            val comments = arrayListOf<Comment>()

            val docClazz: Class<out MutableList<*>?> = ArrayList<String>().javaClass
            val array = get("comments", docClazz)!!

                for (str in array) {
                    val json = JsonUtil.JSON_PARSER.parse(str.toString()).asJsonObject
                    comments.add(
                        Comment(
                            json.get("id").asLong,
                            json.get("username").asString,
                            json.get("context").asString,
                            json.get("timestamp").asLong,
                            json.get("removed").asBoolean,
                            json.get("removedAt").asLong
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
                getLong("closedAt"))
        }
    }

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
            0)
        mongoService.replacePost(post.id, post.toDocument())
        return post
    }

    fun editPost(editPost: EditPost) : Boolean {
        val doc = mongoService.findPostById(editPost.id) ?: return false
        val post = toPost(doc)
        post.lastEdited = Date().time
        post.username = editPost.username
        post.fc = editPost.fc
        post.title = editPost.title
        post.body = editPost.body
        post.tags = editPost.tags
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }

    fun closePost(id: Long) : Boolean {
        val doc = mongoService.findPostById(id) ?: return false
        val post = toPost(doc)
        post.closed = true
        post.closedAt = Date().time
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }

    fun bumpPost(id: Long) : Boolean {
        val doc = mongoService.findPostById(id) ?: return false
        val post = toPost(doc)
        post.lastBumped = Date().time
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }

    fun addComment(id: Long, newComment: NewComment) : Boolean {
        val doc = mongoService.findPostById(id) ?: return false
        val post = toPost(doc)
        val comment = Comment(post.comments.size.toLong()+1, newComment.username, newComment.context, Date().time, false, 0)
        post.comments.add(comment)
        println(post.comments)
        mongoService.replacePost(id, post.toDocument())
        return true
    }

    fun removeComment(postId: Long, commentId: Long) : Boolean {
        val doc = mongoService.findPostById(postId) ?: return false
        val post = toPost(doc)
        val comment = post.comments.find { comment -> comment.id == commentId } ?: return false
        comment.removed = true
        comment.removedAt = Date().time
        mongoService.replacePost(post.id, post.toDocument())
        return true
    }
}