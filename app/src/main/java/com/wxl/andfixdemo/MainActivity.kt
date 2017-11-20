package com.wxl.andfixdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_text.text="版本二没有bug"
        tv_text.setOnClickListener {
            startActivity(Intent(MainActivity@this,LoadActivity::class.java))
        }
    }
}
