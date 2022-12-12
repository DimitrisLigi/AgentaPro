package repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dao.UserDao
import data.User

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}