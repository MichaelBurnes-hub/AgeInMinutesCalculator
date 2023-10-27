package com.example.ageinminutescalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //declaring the textview objects where the output will appear, are set as nullable
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    //onCreate main function and link to activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //declaring and linking button variable for setting date
        val btnDatePicker: Button = findViewById(R.id.btnDate)

        //linking textview for selected date output
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        //linking textview for age in minutes output
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        //onclicklistener makes the button responsive to clicks
        //and calls on the clickDatePicker() function
        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    //function that does all the work for selecting a date and calaculating the age in minutes
    private fun clickDatePicker(){
        //declaring the values for the calendar input and output
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //function is declared as part of a variable to allow for a maxDate to be set on the calendar
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                //Toast is for checking function is operational
                Toast.makeText(
                    this,
                    "Year was $selectedYear, month was ${selectedMonth+1}, " +
                            "day of month was $selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()
                //sets up value format for presentation in textview
                val selectedDate = "${selectedMonth+1}/$selectedDayOfMonth/$selectedYear"

                //sets output textview to the selected date
                tvSelectedDate?.setText(selectedDate)

                //calls SimpleDateFormat function from outside class to set an actual calendar value in english
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

                //adds selectedDate data to a Date value that can actually be manipulated mathematically
                val theDate = sdf.parse(selectedDate)
                //This method of using .let keeps the methods null safe
                //Only running the next functions if the value is not null
                theDate?.let{

                    //converts the time from the selected date and
                    //Jan 1st 1970(note this is the default of the time called function) then converts it into minutes
                    val selectedDateInMinutes = theDate.time / 60000

                    //converts the current date into milliseconds since Jan 1st 1970
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //if current date is not null it runs further functions
                    currentDate?.let{
                        //converts the current date in milliseconds into minutes
                        val currentDateInMinutes = currentDate.time / 60000

                        //minuses the time from the selected date from the current date to get the time in between
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }


            },
                year,
                month,
                day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }

}