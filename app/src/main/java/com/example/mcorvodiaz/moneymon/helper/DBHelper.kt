package com.example.mcorvodiaz.moneymon.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mcorvodiaz.moneymon.model.Operation
import com.example.mcorvodiaz.moneymon.model.Setting


class DBHelper(context: Context?) : SQLiteOpenHelper(context, "Test1.db", null, 1) {

    private val OPERATIONS_TABLE_NAME: String? = "Operations"
    private val SETTINGS_TABLE_NAME: String? = "Settings"

    private val OPERATIONS_COLUMN_ID: String? = "id"
    private val OPERATIONS_COLUMN_PERSON: String? = "Person"
    private val OPERATIONS_COLUMN_DESCRIPTION: String? = "Description"
    private val OPERATIONS_COLUMN_CATEGORY: String? = "Category"
    private val OPERATIONS_COLUMN_DATE: String? = "Date"
    private val OPERATIONS_COLUMN_COST: String? = "Cost"

    private val SETTING_COLUMN_ID: String? = "id"
    private val SETTING_COLUMN_VALUE: String? = "Value"

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable = "" +
                "create table " + OPERATIONS_TABLE_NAME + "" +
                "(" +
                "" + OPERATIONS_COLUMN_ID + " integer primary key autoincrement, " +
                "" + OPERATIONS_COLUMN_PERSON + " varchar(20) not null, " +
                "" + OPERATIONS_COLUMN_DESCRIPTION + " varchar(100) not null, " +
                "" + OPERATIONS_COLUMN_CATEGORY + " archar(20) not null, " +
                "" + OPERATIONS_COLUMN_DATE + " integer not null, " +
                "" + OPERATIONS_COLUMN_COST + " REAL not null" +
                ")"
        db?.execSQL(queryCreateTable)

        val queryCreateTableSetting = "" +
                "create table " + SETTINGS_TABLE_NAME + "" +
                "(" +
                "" + SETTING_COLUMN_ID + " varchar(20) not null, " +
                "" + SETTING_COLUMN_VALUE + " REAL not null " +
                ")"
        db?.execSQL(queryCreateTableSetting)

        if (db != null) {
            addSetting(db, "Initial", 0.0)
            addSetting(db, "Compras", 0.0)
            addSetting(db, "Ocio", 0.0)
        }
//
//        insertSetting(Setting("Initial", 0.0))
//        insertSetting(Setting("Compras", 0.0))
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, newVersion: Int, oldVersion: Int) {
        val queryUpgradeTable = "" +
                "drop table if exists " + OPERATIONS_TABLE_NAME
        sqliteDatabase?.execSQL(queryUpgradeTable)
        onCreate(sqliteDatabase)
    }

    fun insertOperation(operation: Operation): Boolean {
        val sqliteDatabase = writableDatabase
        try {
            val query = "insert into $OPERATIONS_TABLE_NAME " +
                    "values " +
                    "(null, \"${operation.person}\", \"${operation.description}\", \"${operation.category}\", \"${operation.date}\", \"${operation.cost}\")"
            sqliteDatabase.execSQL(query)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun insertSetting(setting: Setting): Boolean {
        val sqliteDatabase = writableDatabase
        try {
            val query = "insert into $SETTINGS_TABLE_NAME " +
                    "values " +
                    "(\"${setting.id}\", \"${setting.value}\",)"
            sqliteDatabase.execSQL(query)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun addSetting(db: SQLiteDatabase, key: String, value: Double) {
        val values = ContentValues()
        values.put(SETTING_COLUMN_ID, key)
        values.put(SETTING_COLUMN_VALUE, value)
        db.insert(SETTINGS_TABLE_NAME, null, values)
    }

    fun updateSetting(setting: Setting): Boolean {
        val sqliteDatabase = writableDatabase
        try {
            val args = ContentValues()
            args.put(SETTING_COLUMN_ID, setting.id)
            args.put(SETTING_COLUMN_VALUE, setting.value)
            sqliteDatabase.update(SETTINGS_TABLE_NAME, args, SETTING_COLUMN_ID + "='" + setting.id + "'", null);
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // Getting single contact
    fun getSetting(id: String): Setting {
        val db = this.readableDatabase
        val cursor = db.query(SETTINGS_TABLE_NAME, arrayOf(SETTING_COLUMN_ID, SETTING_COLUMN_VALUE), SETTING_COLUMN_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor.count == 0) {
            addSetting(db, id, 0.0)
            return Setting(id, 0.0)
        }

        return Setting(cursor.getString(0), cursor.getDouble(1))
    }

    fun getAllData(): ArrayList<Operation> {
        val listStudent = ArrayList<Operation>()
        val sqliteDatabase = this.readableDatabase
        val queryGetAll = "select * from " + OPERATIONS_TABLE_NAME
        val cursor = sqliteDatabase?.rawQuery(queryGetAll, null)
        cursor?.moveToFirst()
        while (cursor?.isAfterLast?.not() as Boolean) {
            val student = Operation(
                    id = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_ID)),
                    person = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_PERSON)),
                    category = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_CATEGORY)),
                    cost = cursor.getDouble(cursor.getColumnIndex(OPERATIONS_COLUMN_COST)),
                    date = cursor.getLong(cursor.getColumnIndex(OPERATIONS_COLUMN_DATE)),
                    description = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_DESCRIPTION))
            )
            listStudent.add(student)
            cursor.moveToNext()
        }
        cursor.close()
        return listStudent
    }

    fun getMonthData(date:Long): ArrayList<Operation> {
        val yeahMonth = date/100
        val yeahMonthMin = yeahMonth * 100
        val yeahMonthMax = yeahMonth * 100 + 99

        val listStudent = ArrayList<Operation>()
        val sqliteDatabase = this.readableDatabase
        val queryGetAll = "select * from " + OPERATIONS_TABLE_NAME + " WHERE date > " + yeahMonthMin + " AND date < " + yeahMonthMax
        val cursor = sqliteDatabase?.rawQuery(queryGetAll, null)
        cursor?.moveToFirst()
        while (cursor?.isAfterLast?.not() as Boolean) {
            val student = Operation(
                    id = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_ID)),
                    person = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_PERSON)),
                    category = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_CATEGORY)),
                    cost = cursor.getDouble(cursor.getColumnIndex(OPERATIONS_COLUMN_COST)),
                    date = cursor.getLong(cursor.getColumnIndex(OPERATIONS_COLUMN_DATE)),
                    description = cursor.getString(cursor.getColumnIndex(OPERATIONS_COLUMN_DESCRIPTION))
            )
            listStudent.add(student)
            cursor.moveToNext()
        }
        cursor.close()
        return listStudent
    }

    fun getTotalMonth(date:Long): Double {
        val list = getMonthData(date)
        var total = 0.0
        for (i in list)
            total=total + i.cost
        return total
    }

    fun getTotalMonthCategory(date:Long, category:String): Double {
        val list = getMonthData(date)
        var total = 0.0
        for (i in list) {
            if (i.category.equals(category)) {
                total = total + i.cost
            }
        }
        return total
    }
}