package com.example.a101d.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.example.a101d.model.Food;
import com.example.a101d.util.Util;

import java.io.ByteArrayOutputStream;
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
                + Util.IN_LIST + " INT, " + Util.IN_CART + " INT, " + Util.FOOD_IMAGE + " BLOB);" ;
        db.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Util.FOOD_TABLE_NAME;
        db.execSQL(DROP_USER_TABLE);
        //, new String[]{Util.FOOD_TABLE_NAME}

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
        contentValues.put(Util.IN_CART, food.getInMyCart());
        //takes image and converts it into a byte array to be stored as a BLOB
        Bitmap image = food.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        //stores the byte array
        contentValues.put(Util.FOOD_IMAGE, byteArray);
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
                food.setInMyCart(cursor.getInt(8));
                //gets BLOB byte array
                byte[] byteArrayImage = cursor.getBlob(9);
                //constructs a bitmap from the byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage , 0, byteArrayImage.length);
                //seting the food image as the bitmap
                food.setImage(bitmap);

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

        String SelectAll = "SELECT * FROM " + Util.FOOD_TABLE_NAME ;
        Cursor cursor = db.rawQuery(SelectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFoodID(cursor.getInt(0));
                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setDate(cursor.getString(3));
                food.setPickUpTime(cursor.getString(4));
                food.setLocation(cursor.getString(5));
                food.setQuantity(cursor.getInt(6));
                food.setInMyList(cursor.getInt(7));
                food.setInMyCart(cursor.getInt(8));
                //gets BLOB byte array
                byte[] byteArrayImage = cursor.getBlob(9);
                //constructs a bitmap from the byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage , 0, byteArrayImage.length);
                //seting the food image as the bitmap
                food.setImage(bitmap);
                foodList.add(food);

            } while (cursor.moveToNext());

        }
        return foodList;
    }

    public Food FetchFood(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Select = "SELECT * FROM " + Util.FOOD_TABLE_NAME + " WHERE " + Util.FOOD_ID +  "=" + id ;
        Cursor cursor = db.rawQuery(Select, null);
        Food food = new Food();
        if (cursor.moveToFirst()) {
            do {
                food.setFoodID(cursor.getInt(0));

                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setDate(cursor.getString(3));
                food.setPickUpTime(cursor.getString(4));
                food.setLocation(cursor.getString(5));
                food.setQuantity(cursor.getInt(6));
                food.setInMyList(cursor.getInt(7));
                food.setInMyCart(cursor.getInt(8));
                //gets BLOB byte array
                byte[] byteArrayImage = cursor.getBlob(9);
                //constructs a bitmap from the byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage , 0, byteArrayImage.length);
                //seting the food image as the bitmap
                food.setImage(bitmap);

            } while (cursor.moveToNext());
        }
        return food;
    }

    public void AddToCart(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Select = "UPDATE " + Util.FOOD_TABLE_NAME + " SET " + Util.IN_CART + "= 1" + " WHERE " + Util.FOOD_ID +  "=" + id ;
        db.execSQL(Select);
    }

    public List<Food> fetchAllFoodInMyCart(){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SelectAll = "SELECT * FROM " + Util.FOOD_TABLE_NAME + " WHERE " + Util.IN_CART +  "=1" ;
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
                food.setInMyCart(cursor.getInt(7));
                //gets BLOB byte array
                byte[] byteArrayImage = cursor.getBlob(9);
                //constructs a bitmap from the byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage , 0, byteArrayImage.length);
                //seting the food image as the bitmap
                food.setImage(bitmap);

                if (cursor.getInt(8) == 0)
                {
                    cursor.moveToNext();
                }
                else
                {
                    food.setInMyCart(1);
                    foodList.add(food);
                }

            } while (cursor.moveToNext());

        }
        return foodList;
    }
}
