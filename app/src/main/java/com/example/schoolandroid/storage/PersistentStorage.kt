package com.example.schoolandroid.storage

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

object PersistentStorage {
    private const val STORAGE_NAME = "SamotokhinSchool"

    private lateinit var settings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(cntxt: Context) {
        settings = cntxt.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()
    }

    fun logoutUser() {
        editor.clear().apply()
    }

    // put from key {name : value}
    fun <T> addProperty(name : String, value : T?) {
        when (value) {
            is String -> editor.putString(name, value)
            is Boolean -> editor.putBoolean(name, value)
            is Int -> editor.putInt(name, value)
            is Float -> editor.putFloat(name, value)
            else -> return
        }
        editor.apply()
    }

    // get from key -> return value
    fun getProperty(name : String, type : String) : Any? {
        return when(type){
            "kotlin.String" -> settings.getString(name, null)
            "kotlin.Float" -> settings.getFloat(name, 0f)
            "kotlin.Int" -> settings.getInt(name, 0)
            "kotlin.Boolean" -> settings.getBoolean(name, false)
            else -> null
        }
    }

    inline fun <reified T : Any> addObject(other: T) {
        val properties = T::class.declaredMemberProperties.associateBy { it.name }
        val constructor = T::class.primaryConstructor
            ?: throw IllegalArgumentException("merge type must have a primary constructor")
        constructor.parameters.associateWith { parameter ->
            val property = properties[parameter.name]
                ?: throw IllegalStateException("no declared member property found with name '${parameter.name}'")
            println(mapOf(Pair(parameter.name!!, property.get(other).toString())))
            if (property.get(other) != null) {
                addProperty(parameter.name!!, property.get(other))
            }
        }
    }

    inline fun <reified T : Any>getObject() : T {
        val properties = T::class.declaredMemberProperties.associateBy { it.name }
        val constructor = T::class.primaryConstructor
            ?: throw IllegalArgumentException("class type need have a primary constructor")
        val args = constructor.parameters.associateWith { parameter ->
            properties[parameter.name]!!.returnType.classifier.toString().split(" ")[1].let {
                return@associateWith getProperty(parameter.name!!, it)
            }
        }
        return constructor.callBy(args)
    }

}
