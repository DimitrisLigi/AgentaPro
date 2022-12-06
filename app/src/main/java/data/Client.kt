package data

data class Client(val clientID: String,
                  val name: String?,
                  val lastName: String?,
                  var address: String?,
                  var phone: String?,
                  val orderList: MutableList<Order>)
