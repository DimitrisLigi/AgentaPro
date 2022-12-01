package classes

import enums.MotorBrand
import enums.MotorNm

class Motor(val brand: MotorBrand, val motorNm: MotorNm, var price: Double) {
    fun printDetails(): String {
        return "${this.brand} ${this.motorNm} ${this.price}"
    }
}