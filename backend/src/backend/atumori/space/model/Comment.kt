package backend.atumori.space.model

data class Comment(
    val id: Long,
    val username: String,
    val context: String,
    val timestamp: Long,
    var removed: Boolean,
    var removedAt: Long,
    var ip: String
)

data class NewComment(
    val username: String,
    val context: String,
    var ip: String
)