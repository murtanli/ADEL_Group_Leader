package com.example.group_leader_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import com.example.group_leader_app.students
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class group_view : AppCompatActivity() {

    private lateinit var container: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_view)

        val exit_button = findViewById<Button>(R.id.exit_but)
        val date_button = findViewById<Button>(R.id.date_but)
        val datetext = findViewById<TextView>(R.id.date_text)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.visibility = View.GONE

        container = findViewById(R.id.container)

        val months: List<String> = listOf(
            "Янв",
            "Фев",
            "Март",
            "Апр",
            "Май",
            "Июнь",
            "Июль",
            "Авг",
            "Сент",
            "Окт",
            "Нояб",
            "Дек")


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        datetext.text = "$dayOfMonth ${months[month]} $year"

        exit_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val sharedPreference = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("login", "false")
            editor.apply()

            startActivity(intent)
        }

        date_button.setOnClickListener {

            // Создаем диалог выбора даты
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"

                datetext.text = "$selectedDay ${months[selectedMonth]} $selectedYear"


                val calendarSelected = Calendar.getInstance()
                calendarSelected.set(selectedYear, selectedMonth, selectedDay)
                val dayOfWeek = calendarSelected.get(Calendar.DAY_OF_WEEK)

                if (dayOfWeek == Calendar.SUNDAY) {
                    val textdate = TextView(this)
                    textdate.setTextAppearance(R.style.Title_style)

                    textdate.textSize = 30F
                    textdate.text = "Выходной"

                    val block_text = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    block_text.gravity = Gravity.CENTER
                    block_text.setMargins(0, 100, 0, 10)
                    textdate.layoutParams = block_text

                    container.removeAllViews()
                    container.addView(textdate)
                } else {
                    val students_cl = students()
                    val names = students_cl.names()

                    container.removeAllViews()
                    var num = 0
                    for (name in names) {
                        num += 1
                        val block = create_students_block(num, name)
                        container.addView(block)
                    }
                    val save_button = Button(this)
                    val but = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    but.setMargins(20, 10, 20, 100)
                    save_button.layoutParams = but
                    save_button.text = "Сохранить"
                    save_button.textSize = 20F

                    container.addView(save_button)


                    save_button.setOnClickListener {
                        progressBar.visibility = View.VISIBLE
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@group_view, "Сохранено !", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }, year, month, dayOfMonth)

            datePickerDialog.show()
        }
    }

    private fun create_students_block(num: Int, name: String): LinearLayout {
        val block = LinearLayout(this)

        val block_params = LinearLayout.LayoutParams(
            1000,
            200
        )
        block_params.setMargins(50, 10, 10, 10)
        block.layoutParams = block_params
        block.orientation = LinearLayout.HORIZONTAL

        val student_num = TextView(this)
        student_num.textSize = 18F
        val num_params = LinearLayout.LayoutParams(
            100,
            200
        )
        student_num.layoutParams = num_params
        student_num.text = num.toString()

        val student_name = TextView(this)
        student_name.textSize = 20F
        val st_nm_params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            300
        )
        st_nm_params.setMargins(20, 20, 20, 0)
        student_name.layoutParams = st_nm_params
        student_name.text = name

        val checkBox = CheckBox(this)
        checkBox.gravity = Gravity.CENTER




        block.addView(student_num)
        block.addView(student_name)
        block.addView(checkBox)


        return block
    }


}
