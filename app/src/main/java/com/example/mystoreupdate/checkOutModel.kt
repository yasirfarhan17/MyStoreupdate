package com.example.mystoreupdate


data class checkOutModel(
    val list: ArrayList<CartModel>? = null,
    val orderId: String? = null,
    val date: String? = null,
    val time: String? = null,
    val add: String? = null,
    val amount: String? = null,
    val paymentMode: String? = null,
    val phone: String? = null,
)

data class CartModel(
    var products_name: String? = null,
    var price: String? = null,
    var img: String? = null,
    var weight: String? = null,
    var quant: String? = null,
    var total: String? = null,
) {
    fun toCartEntity(): CartEntity {
        return CartEntity(
            this.products_name!!,
            this.price,
            this.img,
            this.weight,
            this.quant,
            this.total
        )
    }
}


data class CartEntity(
    val products_name: String = "",
    val price: String? = null,
    val img: String? = null,
    val weight: String? = null,
    var quant: String? = null,
    var total: String? = null,
) {
    fun toProductModel(): CartModel {
        return CartModel(
            this.products_name,
            this.price,
            this.img,
            this.weight,
            this.quant,
            this.total
        )
    }

}
