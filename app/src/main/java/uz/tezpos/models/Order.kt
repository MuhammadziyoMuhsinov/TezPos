package uz.tezpos.models

data class Order(
    var id:String?=null,
    var productId:String?=null,
    var count:Int?=null,
    var price:Int?=null,
    var img:String?=null,
    var name:String?=null
)