package com.example.mcorvodiaz.moneymon.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.mcorvodiaz.moneymon.R
import com.example.mcorvodiaz.moneymon.helper.DBHelper
import com.example.mcorvodiaz.moneymon.helper.DatesHelper
import kotlinx.android.synthetic.main.activity_main.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [OperacionDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(applicationContext, EventActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            applicationContext.startActivity(intent)
        }

        //TODO Mover a otro sitio
        val dbHelper = DBHelper(this)
        var totalComida = dbHelper.getSetting("Compras").value
        var totalOcio = dbHelper.getSetting("Ocio").value
        val totalGastoComida = dbHelper.getTotalMonthCategory(DatesHelper.today(), "Comida")
        val totalGastoOcio = dbHelper.getTotalMonthCategory(DatesHelper.today(), "Ocio")
        comida.text = (totalComida - totalGastoComida).toString()
        ocio.text = (totalOcio - totalGastoOcio).toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_operation_new, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> settingsEvent()
            R.id.action_list -> listEvent()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun settingsEvent() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent)
    }

    private fun listEvent() {
        val intent = Intent(applicationContext, OperacionListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent)
    }
}
