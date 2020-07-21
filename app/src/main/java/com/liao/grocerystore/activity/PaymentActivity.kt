package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liao.grocerystore.R
import com.liao.grocerystore.model.Address
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private val KEY_PAYMENT_METHOD = "payment_method"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        init()
    }

    private fun init() {
        var i = intent
        var myIntent = Intent(this, OrderConfirmationActivity::class.java)

        check_box_1.setOnClickListener {
            if (check_box_1.isChecked) {
                check_box_2.isChecked = false
                myIntent.putExtra(KEY_PAYMENT_METHOD, check_box_1.text.toString())
            }
        }
        check_box_2.setOnClickListener {
            if (check_box_2.isChecked) {
                check_box_1.isChecked = false
                myIntent.putExtra(KEY_PAYMENT_METHOD, check_box_2.text.toString())
            }
        }

        button_payment.setOnClickListener {
            myIntent.putExtra(Address.KEY, i.getSerializableExtra(Address.KEY))
            startActivity(myIntent)
        }
    }
}