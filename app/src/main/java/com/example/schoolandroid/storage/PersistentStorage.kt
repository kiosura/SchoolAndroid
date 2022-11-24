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

    // put from key {name : value}
    fun addProperty(name : String, value : String) {
        editor.putString(name, value)
        editor.apply()
    }

    // get from key -> return value
    fun getProperty(name : String) : String? {
        return settings.getString(name, null)
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
                addProperty(parameter.name!!, property.get(other).toString())
            }
        }
    }

    inline fun <reified T : Any>getObject() : T {
        val constructor = T::class.primaryConstructor
            ?: throw IllegalArgumentException("class type need have a primary constructor")
        val args = constructor.parameters.associateWith {
            return@associateWith getProperty(it.name!!)
        }
        return constructor.callBy(args)
    }

}