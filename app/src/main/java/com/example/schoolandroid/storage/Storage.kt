package com.example.schoolandroid.storage

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.User
import retrofit2.Response
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

//private lateinit var currentCourse : StateFlow<Lessons>
//
//suspend fun setCurrent(index : Int, scope : CoroutineScope) {
//    currentCourse = flow<Lessons> {
//        emit(coursesList.value!![index].lessons)
//    }.stateIn(scope = scope)
//}
//
//fun getLessons() : Result<Lessons> =
//    runCatching { currentCourse.value }
//
//
//private var coursesList : MutableLiveData<Courses> = MutableLiveData()


object Storage {
    private var user : MutableLiveData<User>?  = null
    private var coursesList : MutableLiveData<Courses> = MutableLiveData()
    private lateinit var currentCourse : MutableLiveData<CourseItem>

    fun setUser(userItem : MutableLiveData<Response<User>>) {
        user = MutableLiveData(userItem.value!!.body())
    }

    fun getUser() = user

    fun setCurrent(index : Int) {
        currentCourse = MutableLiveData(coursesList.value!![index])
    }

    fun getCurrentCourse() = currentCourse

    fun addCourses(list : MutableLiveData<Response<Courses>>?) {
        if (coursesList.value == null)
            coursesList = MutableLiveData(list?.value?.body())
        else mergeCourses(list)
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