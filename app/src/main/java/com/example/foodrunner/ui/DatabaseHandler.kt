package com.example.foodrunner.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.foodrunner.ui.home.FavouriteRestaurants

const val DATABASENAME = "FoodRunner"
const val TABLENAME = "favourite"
const val COL_NAME = "name"
const val COL_IMAGE = "image"
const val COL_RATING = "rating"
const val COL_COST = "cost"
const val COL_ID = "id"
class DatabaseHandler(context:Context):SQLiteOpenHelper(context, DATABASENAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE OR REPLACE TABLE " + TABLENAME + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_NAME + " TEXT,"
                + COL_IMAGE + " TEXT," + COL_RATING + " TEXT,"+ COL_COST + " TEXT" +")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        onCreate(db)
    }

    fun insertData(favouriteRestaurants: FavouriteRestaurants){
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID,favouriteRestaurants.id)
        contentValues.put(COL_NAME, favouriteRestaurants.name)
        contentValues.put(COL_IMAGE, favouriteRestaurants.image)
        contentValues.put(COL_RATING,favouriteRestaurants.rating)
        contentValues.put(COL_COST,favouriteRestaurants.cost)
        database.insert(TABLENAME, null, contentValues)
//        if (result == (0).toLong()) {
////            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//            println("Database Error")
//        }
//        else {
////            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//            println("Database Success")
//        }
    }

    fun readData(): MutableList<FavouriteRestaurants> {
        val list: MutableList<FavouriteRestaurants> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                val name = result.getString(result.getColumnIndex(COL_NAME))
                val image = result.getString(result.getColumnIndex(COL_IMAGE))
                val rating = result.getString(result.getColumnIndex(COL_RATING))
                val cost = result.getString(result.getColumnIndex(COL_COST))
                val food = FavouriteRestaurants(id,name,image,rating,cost)
                list.add(food)
            }
            while (result.moveToNext())
        }
        return list
    }
    fun deleteData(favouriteRestaurants: FavouriteRestaurants): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_ID,favouriteRestaurants.id)
        val result = db.delete(TABLENAME,"id="+favouriteRestaurants.id,null)
        db.close()
        return result
    }
}