package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val UID: String,
    var name: String,
    var lastname: String,
    var email: String,
    var clientList: MutableList<Client>?,
    var appointmentList: MutableList<Appointment>?)
