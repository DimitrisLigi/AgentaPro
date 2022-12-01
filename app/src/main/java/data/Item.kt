package data

import enums.MotorBrand
import enums.MotorNm

sealed class Item(val itemID: String){
    data class Motor(val motorBrand: MotorBrand, val motorNm: MotorNm)
}
