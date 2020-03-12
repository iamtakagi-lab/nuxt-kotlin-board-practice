package backend.atumori.space.service

import backend.atumori.space.model.Post
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import io.ktor.config.ApplicationConfig
import org.bson.Document

class MongoService(config: ApplicationConfig) {

    val client: MongoClient
    val database: MongoDatabase
    val posts: MongoCollection<Document>

    init {
        val serverAddress = ServerAddress(config.property("mongo.host").getString(), config.property("mongo.port").getString().toInt())

        if (config.property("mongo.authentication.enabled").getString().toBoolean()) {
            val credential = MongoCredential.createCredential(
                config.property("mongo.authentication.username").getString(),
                config.property("mongo.authentication.database").getString(),
                config.property("mongo.authentication.password").getString().toCharArray()
            )

            this.client = MongoClient(
                serverAddress ,
                listOf<MongoCredential>(credential)
            )
        } else {
            this.client = MongoClient(serverAddress)
        }

        this.database = this.client.getDatabase(config.property("mongo.database").getString())
        this.posts = this.database.getCollection(config.property("mongo.collections.posts_collection").getString())
    }

    fun findPostById(id: Long): Document? {
        return this.posts.find(Filters.eq("id", id)).first() ?: return null
    }

    fun replacePost(id: Long, document: Document) {
        this.posts.replaceOne(
            Filters.eq("id", id), document,
            ReplaceOptions().upsert(true)
        )
    }
}