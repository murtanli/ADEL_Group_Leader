package com.example.group_leader_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logintext = findViewById<EditText>(R.id.inputlogin).text
        val passwordtext = findViewById<EditText>(R.id.inputpassword).text
        val errortext = findViewById<TextView>(R.id.Errortext)
        val button = findViewById<Button>(R.id.button)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE

        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val login_save = sharedPreferences.getString("login", "")

        if (login_save == "true"){
            val intent = Intent(this@MainActivity, group_view::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                if (logintext.toString() == "a" && passwordtext.toString() == "1") {
                    val intent = Intent(this@MainActivity, group_view::class.java)
                    errortext.text = "Успешно !"
                    val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("login", "true")
                    editor.apply()

                    startActivity(intent)
                } else {
                    errortext.text = "Неправильно введен логин либо пароль"
                }
                progressBar.visibility = View.GONE // Скрываем ProgressBar после завершения операции
            }
        }

    }


}