package com.example.mcorvodiaz.moneymon.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mcorvodiaz.moneymon.model.Operation
import com.example.mcorvodiaz.moneymon.R
import com.example.mcorvodiaz.moneymon.helper.DBHelper
import com.example.mcorvodiaz.moneymon.helper.DatesHelper
import kotlinx.android.synthetic.main.activity_event.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [OperacionDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var cal = Calendar.getInstance()

        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateText.setText(sdf.format(cal.time))

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateText.setText(sdf.format(cal.time))

        }

        dateText.setOnClickListener {
            DatePickerDialog(this@EventActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        btnDisplay.setOnClickListener({
            val date = DatesHelper.parseToLong(dateText.text.toString())
            val operation = Operation(
                    person = "Maaike",
                    cost = WasteText.text.toString().toDouble(),
                    date = date,
                    category = categorySpin.selectedItem.toString(),
                    description = descriptionText.text.toString()
            )
            val dbHelper = DBHelper(this)
            dbHelper.insertOperation(operation)
            finish()
        })

    }
}
