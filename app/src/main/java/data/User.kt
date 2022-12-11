package data

data class User(val UID: String,
                var name: String,
                var lastname: String,
                var email: String,
                var clientList: MutableList<Client>?,
                var appointmentList: MutableList<Appointment>?)
