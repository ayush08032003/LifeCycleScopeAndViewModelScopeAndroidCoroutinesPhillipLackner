package com.example.lifecyclescopeandviewmodelscope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "debugAyush"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartActivity : Button = findViewById(R.id.btnStartActivity)
        btnStartActivity.setOnClickListener {
            lifecycleScope.launch { // view notes about lifecycleScope
                while(true){
                    delay(1000L)
                    Log.d(TAG,"Still Running ...!")
                }
            }
            lifecycleScope.launch {
                delay(5000L)
                Intent(this@MainActivity, SecondActivity::class.java).also{
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}

/*
Even after we get to second activity, and destroys the firstActivity, the infinite loop we created stills goes on..
Even if we press back button in our phones, it doesnot stop the infinite loop, This will create a memory leak and it is a very big mistake

When you need to use lifecycleScope, add
implementation 'androidx.navigation:navigation-fragment-ktx:2.5.2'
implementation 'androidx.navigation:navigation-ui-ktx:2.5.2'
to Gradle(:app)

lifecycleScope will attach the lifecycle of the coroutine with our activity. When the activity destroyed, all coroutines launched in this lifecycleScope
will also be destroyed.
 */