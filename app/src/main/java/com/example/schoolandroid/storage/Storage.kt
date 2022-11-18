package com.example.schoolandroid.storage

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.Lessons
import com.example.schoolandroid.screens.course.lesson
import com.example.schoolandroid.screens.main.courses
import retrofit2.Response
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

object Storage {
    private var currentCourse : Int = 0

    fun setCurrent(index : Int) {
        currentCourse = index
    }

    fun getCurrent() = coursesList.value!![currentCourse]
    fun getLessons() = MutableLiveData(coursesList.value!![currentCourse].lessons)


    private var coursesList : MutableLiveData<Courses> = MutableLiveData()

    fun addCourses(list : MutableLiveData<Response<Courses>>?) {
        coursesList = MutableLiveData(list?.value?.body())
    }

    fun getCourses() = coursesList

    fun mergeCourses(list : MutableLiveData<Response<Courses>>?) {
        val courseWithLessons = MutableLiveData(list?.value?.body())
        if (courseWithLessons.value != null) {
            for (i in 0..coursesList.value!!.size - 1) {
                for (k in 0..courseWithLessons.value!!.size - 1) {
                    if (coursesList.value!![i].id == courseWithLessons.value!![k].id) {
                        coursesList.value!![i] =
                            coursesList.value!![i] merge courseWithLessons.value!![k]
                    }
                }
            }
        }
    }

    inline infix fun <reified T : Any> T.merge(other: T): T {
        val propertiesByName = T::class.declaredMemberProperties.associateBy { it.name }
        val primaryConstructor = T::class.primaryConstructor
            ?: throw IllegalArgumentException("merge type must have a primary constructor")
        val args = primaryConstructor.parameters.associateWith { parameter ->
            val property = propertiesByName[parameter.name]
                ?: throw IllegalStateException("no declared member property found with name '${parameter.name}'")
            (property.get(this) ?: property.get(other))
        }
        return primaryConstructor.callBy(args)
    }
}