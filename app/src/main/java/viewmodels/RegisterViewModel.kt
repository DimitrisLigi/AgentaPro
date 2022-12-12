package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import data.User
import db.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repositories.UserRepository

class RegisterViewModel(application: Application): AndroidViewModel(application) {
    private val _readAllData: LiveData<List<User>>
    private val _repository: UserRepository

    init {
        val userDao = UserDatabase.getUserDatabase(application).userDao()
        _repository = UserRepository(userDao)
        _readAllData = _repository.readAllData
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            _repository.addUser(user)
        }
    }
}