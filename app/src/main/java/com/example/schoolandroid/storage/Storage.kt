package com.example.schoolandroid.storage

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.LessonItem
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
    private var currentCourse : MutableLiveData<CourseItem> = MutableLiveData()

    fun setUser(userItem : User?) {
        if (userItem != null && userItem.error_message == null) {
            PersistentStorage.addProperty("name", userItem.name.toString())
        }
        user = MutableLiveData(userItem)
        user!!.postValue(userItem)
    }

    fun setUser(userItem : User, is_added : Boolean){
        if (is_added) {
            //needs some actions with PersistentStorage: add new fields for SharedPref

            val newUser = User() merge userItem
            user = MutableLiveData(newUser merge user?.value!!)
            user!!.postValue(user?.value)
        }
        else {
            user = MutableLiveData(userItem)
            user!!.postValue(userItem)
        }
    }

    fun getUser() = user

    fun setCurrent(index : Int) {
        currentCourse = MutableLiveData(coursesList.value!![index])
    }

    fun getCurrentCourse() = currentCourse

    fun updateLesson(lesson : LessonItem?) {
        if (lesson != null) {
            val index = lesson.index
            for (i in 0 until currentCourse.value!!.lessons.size) {
                if (currentCourse.value!!.lessons[i].index == index) {
                    currentCourse.value!!.lessons[i] =
                        currentCourse.value!!.lessons[i] merge lesson
                    currentCourse.postValue(currentCourse.value)
                }
            }
        }
        else println("lesson is null")
    }

    fun getLesson(index: Int) : MutableLiveData<LessonItem>
        = MutableLiveData(currentCourse.value!!.lessons[index])

    fun addCourses(list : MutableLiveData<Response<Courses>>?) {
        if (coursesList.value == null)
            coursesList = MutableLiveData(list?.value?.body())
        else mergeCourses(list)
    }

    fun getCourses() = coursesList

    fun mergeCourses(list : MutableLiveData<Response<Courses>>?) {
        val courseWithLessons = MutableLiveData(list?.value?.body())
        if (courseWithLessons.value != null) {
            for (i in 0 until coursesList.value!!.size) {
                for (k in 0 until courseWithLessons.value!!.size) {
                    if (coursesList.value!![i].id == courseWithLessons.value!![k].id) {
                        coursesList.value!![i] =
                            coursesList.value!![i] merge courseWithLessons.value!![k]
                    }
                }
            }
        }
    }

    private inline infix fun <reified T : Any> T.merge(other: T): T {
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