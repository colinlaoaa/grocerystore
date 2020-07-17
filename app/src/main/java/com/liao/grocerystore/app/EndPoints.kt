package com.liao.grocerystore.app


class Endpoints {

    companion object {

        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory"
        private const val URL_PRODUCTS = "products/sub"
        private const val URL_LOGIN = "auth/login"
        private const val URL_REGISTER = "auth/register"

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

    }

}