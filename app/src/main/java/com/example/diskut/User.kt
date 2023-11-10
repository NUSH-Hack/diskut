package com.example.diskut

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class User (
    val email: String,
    val name: String,
    val year: Int,
    val points: Int
) {
    val majors: ArrayList<String> = arrayListOf()
    val teachers: ArrayList<String> = arrayListOf()

    companion object {
        fun deserialize(serialized: ByteArray) : User {
            return Json.decodeFromString(serialized.toString())
        }
    }

    fun addMajor(major: String) {
        majors.add(major)
    }
    fun addTeacher(teacher: String) {
        teachers.add(teacher)
    }

    fun findCommonMajor(other: User): Set<String> {
        return other.majors.intersect(majors)
    }

    fun findCommonTeacher(other: User): Set<String> {
        return other.teachers.intersect(teachers)
    }

    fun serialize(): ByteArray {
        // TODO: Chnage this to whatever is needed for Bluetooth communication
        return Json.encodeToString(this).toByteArray()
    }



}
