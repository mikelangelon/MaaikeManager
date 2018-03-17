package com.example.mcorvodiaz.moneymon.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.mcorvodiaz.moneymon.OperacionDetailActivity
import com.example.mcorvodiaz.moneymon.OperacionDetailFragment
import com.example.mcorvodiaz.moneymon.R
import com.example.mcorvodiaz.moneymon.helper.DBHelper
import com.example.mcorvodiaz.moneymon.helper.DatesHelper
import com.example.mcorvodiaz.moneymon.model.Operation
import kotlinx.android.synthetic.main.activity_operacion_list.*
import kotlinx.android.synthetic.main.operacion_list.*
import kotlinx.android.synthetic.main.operacion_list_content.view.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [OperacionDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class OperacionListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operacion_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (operacion_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        setupRecyclerView(operacion_list)
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
        Toast.makeText(getApplicationContext(), "This is my Toast message!",
                Toast.LENGTH_LONG).show()
    }

    private fun listEvent() {
        Toast.makeText(getApplicationContext(), "List!",
                Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val dbHelper = DBHelper(this)
        total.text = dbHelper.getTotalMonth(DatesHelper.today()).toString()
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, dbHelper.getMonthData(DatesHelper.today()), mTwoPane)
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: OperacionListActivity,
                                        private val mValues: List<Operation>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as Operation
                if (mTwoPane) {
                    val fragment = OperacionDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(OperacionDetailFragment.ARG_ITEM_ID, item.date.toString())
                        }
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.operacion_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, OperacionDetailActivity::class.java).apply {
                        putExtra(OperacionDetailFragment.ARG_ITEM_ID, item.date)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.operacion_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mIdView.text = DatesHelper.parseToString(item.date)
            holder.mContentView.text = item.description
            holder.costView.text = item.cost.toString()

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mContentView: TextView = mView.content
            val costView: TextView = mView.cost
        }
    }
}
