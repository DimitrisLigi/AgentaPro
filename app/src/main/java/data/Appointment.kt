package data

import enums.AppointmentNature

data class Appointment(val client: Client,
                       var appointmentTime: String,
                       var appointmentNature: AppointmentNature) {
}