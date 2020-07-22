package com.liao.grocerystore.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.liao.grocerystore.model.CartContent

class DBHelper(var mContext: Context) : SQLiteOpenHelper(
    mContext, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "GROCERY"
        const val TABLE_NAME = "CART"
        const val DATABASE_VERSION = 1
        const val COL_ID = "_id"
        const val COL_PRODUCTNAME = "productName"
        const val COL_PRICE = "price"
        const val COL_MRP = "mrp"
        const val COL_IMAGE = "image"
        const val COL_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createtable =
            "create table $TABLE_NAME ($COL_ID char(50), $COL_PRODUCTNAME char(50), $COL_PRICE double, $COL_MRP double, $COL_IMAGE char(50),$COL_QUANTITY integer)"
        db?.execSQL(createtable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("db", "onUpgrade")
    }

    fun addquantity(_id: String): Boolean {
        var db = writableDatabase
        val whereClause = arrayOf(_id)
        val query = "select * from $TABLE_NAME where $COL_ID = ?"
        var cursor = db.rawQuery(query, whereClause)
        var count = cursor.count
        return count != 0


    }

    fun addCartContent(addCartContent: CartContent) {
        var db = writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COL_ID, addCartContent._id)
        contentValues.put(COL_PRODUCTNAME, addCartContent.productName)
        contentValues.put(COL_PRICE, addCartContent.price)
        contentValues.put(COL_MRP, addCartContent.mrp)
        contentValues.put(COL_IMAGE, addCartContent.image)
        contentValues.put(COL_QUANTITY, addCartContent.quantity)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun readCartContentItemQuantity(_id: String): Int {
        var db = writableDatabase
        var cartList = readCartContent()
        for (cartItem in cartList) {
            if (cartItem._id == _id) {
                return cartItem.quantity
            }
        }
        return 0
    }


    fun deleteCartContent(_id: String) {
        var db = writableDatabase
        val whereClause = "$COL_ID = ?"
        val whereArgs = arrayOf(_id)
        db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun clearCartContent(){
        var db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }


    fun updateCartContent(updateCartContent: CartContent) {
        var db = writableDatabase
        val whereClause = "$COL_ID = ?"
        val whereArgs = arrayOf(updateCartContent._id)
        val contentValues = ContentValues()
        contentValues.put(COL_PRODUCTNAME, updateCartContent.productName)
        contentValues.put(COL_PRICE, updateCartContent.price)
        contentValues.put(COL_MRP, updateCartContent.mrp)
        contentValues.put(COL_IMAGE, updateCartContent.image)
        contentValues.put(COL_QUANTITY, updateCartContent.quantity)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }


    fun checkoutTotal(): ArrayList<Double> {
        var subtotal: Double = 0.0
        var saving: Double = 0.0
        var tax: Double = 0.0
        var total:Double = 0.0
        var db = writableDatabase
        var cartContentList: ArrayList<CartContent> = ArrayList()
        var columns = arrayOf(
            COL_PRICE,
            COL_MRP,
            COL_QUANTITY
        )
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
                var price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COL_MRP))
                subtotal += quantity*price
                saving+= quantity*(mrp - price)
                tax += quantity*price*0.02
                total += quantity*price*1.02
            } while (cursor.moveToNext())
            cursor.close()

        }
        var arrayList = ArrayList<Double>()
        arrayList.add(subtotal)
        arrayList.add(saving)
        arrayList.add(tax)
        arrayList.add(total)
        return arrayList
    }


    fun readCartContent(): ArrayList<CartContent> {
        var db = writableDatabase
        var cartContentList: ArrayList<CartContent> = ArrayList()
        var columns = arrayOf(
            COL_ID,
            COL_PRODUCTNAME,
            COL_PRICE,
            COL_MRP,
            COL_IMAGE,
            COL_QUANTITY
        )
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COL_ID))
                var productName = cursor.getString(cursor.getColumnIndex(COL_PRODUCTNAME))
                var price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COL_MRP))
                var image = cursor.getString(cursor.getColumnIndex(COL_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
                var cartContent = CartContent(id, productName, price, mrp, image, quantity)
                cartContentList.add(cartContent)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return cartContentList
    }

}