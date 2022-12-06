package data

data class Order(val orderID: String, val itemList: MutableList<Item<Any>>, val total: Double)
