package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.liao.grocerystore.R
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Address
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private val KEY_PAYMENT_METHOD = "payment_method"
    private val NAME = "NAME"
    private val MOBILE = "MOBILE"
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        toolbar("Payment")


        init()
    }

    private fun init() {
        sessionManager = SessionManager(this)
        text_view_1.text = sessionManager.getUserName()
        text_view_2.text = sessionManager.getMobile()
        linear_1.visibility = View.GONE
        linear_11.visibility = View.GONE
        button_save.visibility = View.GONE

        button_alter.setOnClickListener {
            linear_2.visibility = View.GONE
            linear_22.visibility = View.GONE
            button_alter.visibility = View.GONE
            linear_1.visibility = View.VISIBLE
            linear_11.visibility = View.VISIBLE
            button_save.visibility = View.VISIBLE
            edit_text_1.setText(sessionManager.getUserName())
            edit_text_2.setText(sessionManager.getMobile())
        }

        button_save.setOnClickListener {
            var name = edit_text_1.text.toString()
            var mobile = edit_text_2.text.toString()
            linear_1.visibility = View.GONE
            linear_11.visibility = View.GONE
            button_save.visibility = View.GONE
            linear_2.visibility = View.VISIBLE
            linear_22.visibility = View.VISIBLE
            button_alter.visibility = View.VISIBLE
            text_view_1.text = name
            text_view_2.text = mobile

        }


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
            if ((check_box_1.isChecked || check_box_2.isChecked) && button_alter.visibility==View.VISIBLE) {
                myIntent.putExtra(NAME, text_view_1.text.toString())
                myIntent.putExtra(MOBILE, text_view_2.text.toString())
                myIntent.putExtra(Address.KEY, i.getSerializableExtra(Address.KEY))
                startActivity(myIntent)
            } else {
                var snackBar = Snackbar.make(layout, "you must choose one payment method", Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}