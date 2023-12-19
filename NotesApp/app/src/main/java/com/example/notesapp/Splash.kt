package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val obj: Thread = object : Thread(){
            override fun run() {
                try {
                    sleep(2000)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
                finally {
                    val intent  = Intent(this@Splash , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        obj.start()
    }
}