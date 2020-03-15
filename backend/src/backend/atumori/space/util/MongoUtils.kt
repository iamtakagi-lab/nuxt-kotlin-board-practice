package backend.atumori.space.util

import com.mongodb.client.model.Filters
import org.bson.conversions.Bson

object MongoUtils {

    fun find(key: String, value: Any): Bson {
        return Filters.eq(key, value)
    }
}
