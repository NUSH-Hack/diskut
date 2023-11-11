package com.example.diskut.Model

//import com.ml.shubham0204.glove_android.GloVe
import com.ml.shubham0204.glove_android.GloVe
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class User (
    var email: String,
    var name: String,
    val type: UserType,
    var value: String, // Year if Student, Department if Teacher, Job if Staff
    val points: Int
) {
    val majors: ArrayList<String> = arrayListOf()
    val teachers: ArrayList<String> = arrayListOf()
    val interests: ArrayList<String> = arrayListOf()

    companion object {
        fun deserialize(serialized: ByteArray) : User {
            return Json.decodeFromString(serialized.decodeToString().trim { it < ' ' } )
        }
    }

    fun addMajor(major: String) {
        majors.add(major)
    }
    fun addTeacher(teacher: String) {
        teachers.add(teacher)
    }

    fun addInterest(interest: String) {
        interests.add(interest)
    }

    fun findCommonMajor(other: User): Set<String> {
        return other.majors.intersect(majors.toSet())
    }

    fun findCommonTeacher(other: User): Set<String> {
        return other.teachers.intersect(teachers.toSet())
    }

    suspend fun findCommonInterests(other: User): ArrayList<InterestSimilarityPair> {
        var embeddings : GloVe.GloVeEmbeddings? = null
        GloVe.loadEmbeddings {
            embeddings = it
        }.join()
        val similarities: ArrayList<InterestSimilarityPair> = arrayListOf()
        interests.forEach { myInterest ->
            other.interests.forEach {otherInterest ->
                if (embeddings != null) {
                    val similarity = GloVe.compare(
                        embeddings!!.getEmbedding(myInterest),
                        embeddings!!.getEmbedding(otherInterest)
                    )
                    similarities.add(InterestSimilarityPair(myInterest, otherInterest, similarity))
                }
            }
        }
        similarities.sortBy { it.similarity }
        similarities.reverse()
        return similarities
    }

    fun serialize(): ByteArray {
        // TODO: Chnage this to whatever is needed for Bluetooth communication
        return Json.encodeToString(this).toByteArray()
    }
}
