package backend.atumori.space.model

data class Comment(
    val id: Long,
    val username: String,
    val context: String,
    val timestamp: Long,
    var removed: Boolean,
    var removedAt: Long
)

data class NewComment(
    val username: String,
    val context: String
)