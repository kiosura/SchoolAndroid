package com.example.schoolandroid.storage

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.*
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
    private var user : MutableLiveData<User>  = MutableLiveData()
    private var coursesList : MutableLiveData<Courses>? = null
    private var myCoursesList : MutableLiveData<Courses>? = null
    private var currentCourse : MutableLiveData<CourseItem> = MutableLiveData()

    fun setUser(userItem : User) {
        if (userItem.error_message == null) {
            PersistentStorage.addObject(userItem)
        }
        user = MutableLiveData(userItem)
        user.postValue(userItem)
    }

    fun setUser(userItem : User, is_added : Boolean){
        if (is_added && user.value != null) {
            user = MutableLiveData(userItem merge user.value!!)
            user.postValue(user.value)

            PersistentStorage.addObject(user.value!!)
        }
        else {
            user = MutableLiveData(userItem)
            user.postValue(userItem)
        }
    }

    fun setProgresses(progresses: Progresses) {
        user.value!!.progresses = progresses
        user.postValue(user.value)
    }

    fun getUser() = user

    fun setCurrent(index : Int, isMy: Boolean = false) {
        if (isMy) currentCourse = MutableLiveData(myCoursesList?.value!![index])
        else currentCourse = MutableLiveData(coursesList?.value!![index])
    }

    fun getCurrentCourse() = currentCourse

    fun updateLesson(lesson : LessonItem?, index : Int) {
        if (lesson != null) {
            for (i in 0 until currentCourse.value!!.lessons.size) {
                if (currentCourse.value!!.lessons[i].index == index) {
                    currentCourse.value!!.lessons[i] =
                        currentCourse.value!!.lessons[i] merge lesson
                    currentCourse.postValue(currentCourse.value)
                }
            }
        }
    }

    fun getLesson(index: Int) : MutableLiveData<LessonItem>
        = MutableLiveData(currentCourse.value!!.lessons[index])

    fun addCourses(list : Courses?, isMy : Boolean = false) {
        if (isMy) {
            if (myCoursesList?.value == null)
                myCoursesList = MutableLiveData(list)
            else mergeCourses(list, isMy)
        }
        else {
            if (coursesList?.value == null)
                coursesList = MutableLiveData(list)
            else mergeCourses(list)
        }
    }

    fun clearCourses(isMy: Boolean = false) {
        if (isMy) myCoursesList = MutableLiveData()
        else coursesList = MutableLiveData()
    }

    fun getCourses(isMy: Boolean = false) : MutableLiveData<Courses>? {
        if (isMy) return myCoursesList
        else return coursesList
    }

    fun mergeCourses(list : Courses?, isMy: Boolean = false) {
        if (list != null) {
            val oldList : MutableLiveData<Courses>
            if (isMy) oldList = myCoursesList!!
            else oldList = coursesList!!
            for (i in 0 until oldList.value!!.size) {
                for (k in 0 until list.size) {
                    if (oldList.value!![i].id == list[k].id) {
                        oldList.value!![i] =
                            oldList.value!![i] merge list[k]
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