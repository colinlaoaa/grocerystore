package com.liao.grocerystore.app


class Endpoints {

    companion object {

        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory"
        private const val URL_PRODUCTS = "products/sub"
        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"
        private const val URL_ADDRESS = "address"
        private const val URL_ORDERS = "orders"
        private const val URL_USER = "users"

        fun getCategory(): String {
            return Config.BASE_URL + URL_CATEGORY
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY}/$catId"
        }

        fun getProductsBySubId(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCTS}/$subId"
        }

        fun getLogin(): String {
            return "${Config.BASE_URL + URL_LOGIN}"
        }

        fun getRegister(): String {
            return "${Config.BASE_URL + URL_REGISTER}"
        }

        fun uploadAddress(): String {
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun readAddress(userId: String?): String {
            return "${Config.BASE_URL + URL_ADDRESS}/$userId"
        }

        fun sendOrders(): String{
            return "${Config.BASE_URL + URL_ORDERS}"
        }

        fun updateUser(userId: String?): String{
            return "${Config.BASE_URL + URL_USER}/$userId"
        }

    }

}