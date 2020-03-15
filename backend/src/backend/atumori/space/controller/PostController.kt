package backend.atumori.space.controller

import backend.atumori.space.model.*
import backend.atumori.space.service.CommentService
import backend.atumori.space.service.MongoService
import backend.atumori.space.service.PostService
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.postController(mongoService: MongoService, postService: PostService, commentService: CommentService) {


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
                        call.respond(PostService.toPost(post))
                    }
                }

                get("comments"){
                    val id = call.parameters["id"]!!.toLong()

                    val comments = commentService.getComments(id)
                    if(comments == null) call.respond(HttpStatusCode.NotFound)
                    call.response.status(HttpStatusCode.OK)
                    if(comments != null){
                        call.respond(comments.toString())
                    }
                }

                route("comment") {

                    // /post/{id}/comment
                    post {
                        val id = call.parameters["id"]!!.toLong()
                        val newcomment = call.receive<NewComment>()
                        val added = commentService.addComment(id, newcomment)
                        if (added) {
                            call.response.status(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.NotFound)
                        }
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