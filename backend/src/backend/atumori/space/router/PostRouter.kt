package backend.atumori.space.router

import backend.atumori.space.model.*
import backend.atumori.space.service.MongoService
import backend.atumori.space.service.PostService
import backend.atumori.space.util.EncryptionUtil
import backend.atumori.space.util.JsonUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.util.JSON
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.parseAuthorizationHeader
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import io.ktor.request.host
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.postRouter(mongoService: MongoService, postService: PostService) {


        get("/posts") {
            call.respond(mongoService.posts.find()
                .sort(Sorts.orderBy(Sorts.descending("publishedAt")))
                .sort(Sorts.orderBy(Sorts.descending("lastBumped"))).toList())
        }

        get("/posts/{type}") {
            val type = call.parameters["type"]
            call.respond(mongoService.posts.find(Filters.eq("type", type))
                .sort(Sorts.orderBy(Sorts.descending("publishedAt")))
                .sort(Sorts.orderBy(Sorts.descending("lastBumped")))
                .toList())
        }

        route("/post") {


            route("{id}") {

                get {
                    val id = call.parameters["id"]!!.toLong()
                    val post = mongoService.findPostById(id)
                    if(post == null) call.respond(HttpStatusCode.NotFound)
                    call.response.status(HttpStatusCode.OK)
                    if (post != null) {
                        call.respond(postService.toPost(post))
                    }
                }

                post("/comment") {
                    val id = call.parameters["id"]!!.toLong()
                    val newcomment = call.receive<NewComment>()
                    val added = postService.addComment(id, newcomment)
                    println(newcomment)
                    if(added) {
                        call.response.status(HttpStatusCode.OK)
                    }else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
                delete("/comment/{commentId}"){
                    val postId = call.parameters["id"]!!.toLong()
                    val commentId = call.parameters["commentId"]!!.toLong()
                    val removed = postService.removeComment(postId, commentId)
                    if(removed) {
                        call.response.status(HttpStatusCode.OK)
                    }else{
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }

            post {
                val newpost = call.receive<NewPost>()
                val added = postService.addPost(newpost)
                call.response.status(HttpStatusCode.OK)
                call.respond(added)
            }

            put("/edit"){
                val editPost = call.receive<EditPost>()
                val edited = postService.editPost(editPost)
                if(edited) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }

            delete("{id}") {
                val id = call.parameters["id"]!!.toLong()
                val closed = postService.closePost(id)
                if(closed) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }

            post("/bump/{id}") {
                val id = call.parameters["id"]!!.toLong()
                val bumped = postService.bumpPost(id)
                if(bumped) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }

}