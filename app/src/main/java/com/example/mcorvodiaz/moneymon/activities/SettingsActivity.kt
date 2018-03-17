package com.example.mcorvodiaz.moneymon.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mcorvodiaz.moneymon.R
import com.example.mcorvodiaz.moneymon.helper.DBHelper
import com.example.mcorvodiaz.moneymon.model.Setting
import kotlinx.android.synthetic.main.settings.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [OperacionDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        txtInitial.setText("0.0")
        txtCompras.setText("0.0")
        txtOcio.setText("0.0")
        val dbHelper = DBHelper(this)
        if (dbHelper.getSetting("Initial") != null) {
            txtInitial.setText(dbHelper.getSetting("Initial").value.toString())
            txtCompras.setText(dbHelper.getSetting("Compras").value.toString())
            txtOcio.setText(dbHelper.getSetting("Ocio").value.toString())
        }

        btnSubmit.setOnClickListener { view ->
            dbHelper.updateSetting(Setting("Initial", txtInitial.text.toString().toDouble()))
            dbHelper.updateSetting(Setting("Compras", txtCompras.text.toString().toDouble()))
            dbHelper.updateSetting(Setting("Ocio", txtCompras.text.toString().toDouble()))
            finish()
        }
    }
}
