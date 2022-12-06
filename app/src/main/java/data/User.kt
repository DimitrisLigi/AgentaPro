package data

data class User(val UID: String,
                var name: String,
                var lastname: String,
                var email: String,
                val clientList: MutableList<Client>?,
                val appointmentList: MutableList<Appointment>?)
