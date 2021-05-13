package com.example.a61d.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a61d.model.Food;
import com.example.a61d.util.Util;

import java.util.ArrayList;
import java.util.List;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    public FoodDatabaseHelper(@Nullable Context context) {
        super(context, Util.FOOD_DATABASE_NAME, null, Util.FOOD_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOOD_TABLE = "CREATE TABLE " + Util.FOOD_TABLE_NAME + " (" + Util.FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.FOOD_TITLE + " TEXT, " + Util.FOOD_DESCRIPTION + " TEXT, " + Util.FOOD_DATE + " TEXT, "
                + Util.FOOD_TIME + " TEXT, " + Util.FOOD_LOCATION + " TEXT, " + Util.FOOD_QUANTITY + " INT, "
                + Util.IN_LIST + " INT);";
        db.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        db.execSQL(DROP_USER_TABLE, new String[]{Util.FOOD_TABLE_NAME});

        onCreate(db);
    }

    public long insertFood(Food food)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.FOOD_TITLE, food.getTitle());
        contentValues.put(Util.FOOD_DESCRIPTION, food.getDescription());
        contentValues.put(Util.FOOD_DATE, food.getDate());
        contentValues.put(Util.FOOD_TIME, food.getPickUpTime());
        contentValues.put(Util.FOOD_LOCATION, food.getLocation());
        contentValues.put(Util.FOOD_QUANTITY, food.getQuantity());
        contentValues.put(Util.IN_LIST, food.getInMyList());
        long newRowID = db.insert(Util.FOOD_TABLE_NAME, null, contentValues);
        db.close();
        return newRowID;
    }

    public List<Food> fetchAllFoodInMyList(){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SelectAll = "SELECT * FROM " + Util.FOOD_TABLE_NAME + " WHERE " + Util.IN_LIST +  "=1" ;
        Cursor cursor = db.rawQuery(SelectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setDate(cursor.getString(3));
                food.setPickUpTime(cursor.getString(4));
                food.setLocation(cursor.getString(5));
                food.setQuantity(cursor.getInt(6));

                if (cursor.getInt(7) == 0)
                {
                    cursor.moveToNext();
                }
                else
                {
                    food.setInMyList(1);
                    foodList.add(food);
                }

            } while (cursor.moveToNext());

        }
        return foodList;
    }

    public List<Food> fetchAllFood(){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SelectAll = "SELECT * FROM " + Util.FOOD_TABLE_NAME + " WHERE " + Util.IN_LIST +  "=1" ;
        Cursor cursor = db.rawQuery(SelectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setDate(cursor.getString(3));
                food.setPickUpTime(cursor.getString(4));
                food.setLocation(cursor.getString(5));
                food.setQuantity(cursor.getInt(6));
                food.setInMyList(cursor.getInt(7));
                foodList.add(food);

            } while (cursor.moveToNext());

        }
        return foodList;
    }
}
