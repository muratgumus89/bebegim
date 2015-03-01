package com.example.murat.benimbebegim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class CreateBabyDataBaseAdapter 
{
		static final String DATABASE_NAME = "babies.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String DATABASE_CREATEBABE = "create table "+"BABY"+
		  "( " +"ID"+" integer primary key autoincrement,"+ "BABYNAME  text," +
		  "GENDER  text,DATE  text,TIME  text,WEIGHT double,HEIGHT double,IMAGE text,USER_ID integer); ";
		//"FOREIGN KEY (ID) references USERS (ID) on update restrict on delete restrict
		// Variable to hold the database instance
		public  SQLiteDatabase dbBaby;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private CreateBabyDataBaseHelper dbHelperBaby;
		public  CreateBabyDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelperBaby = new CreateBabyDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  CreateBabyDataBaseAdapter open() throws SQLException 
		{
			dbBaby = dbHelperBaby.getWritableDatabase();
			return this;
		}
		public void close() 
		{
			dbBaby.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return dbBaby;
		}

		public void insertEntryCreateBaby(String babyName,String gender,String date,String time,Double weight,Double height,String image,Integer userId)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("BABYNAME", babyName);
			newValues.put("GENDER", gender);
			newValues.put("DATE", date);
			newValues.put("TIME", time);
			newValues.put("WEIGHT", weight);
			newValues.put("HEIGHT",height);
			newValues.put("IMAGE",image);
			newValues.put("USER_ID",userId);
			
			
			// Insert the row into your table
			dbBaby.insert("BABY", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		public int deleteEntryCreateBaby(String id)
		{
			//String id=String.valueOf(ID);
		    String where="ID=?";
		    int numberOFEntriesDeleted= dbBaby.delete("BABY", where, new String[]{id}) ;
	        Toast.makeText(context, numberOFEntriesDeleted +" Kay�t Ba�ar�yla Silindi.", Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}
		
		public String getSinlgeEntryCreateBaby(String babyName)
		{
			Cursor cursor=dbBaby.query("BABY", null, " BABYNAME=?", new String[]{babyName}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("IMAGE"));
			cursor.close();
			return password;				
		}
		public String getGender(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String gender= cursor.getString(cursor.getColumnIndex("GENDER"));
			cursor.close();
			return gender;				
		}
		public String getImage(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String image= cursor.getString(cursor.getColumnIndex("IMAGE"));
			cursor.close();
			return image;				
		}
		public String getBabyId(String babyName)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=?", new String[]{babyName}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String babyID= cursor.getString(cursor.getColumnIndex("ID"));
			cursor.close();
			return babyID;				
		}
		public String getBabyName(String id)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=?", new String[]{id}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String babyName= cursor.getString(cursor.getColumnIndex("BABYNAME"));
			cursor.close();
			return babyName;				
		}
		public String getDate(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String date= cursor.getString(cursor.getColumnIndex("DATE"));
			cursor.close();
			return date;				
		}
		public String getTime(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String time= cursor.getString(cursor.getColumnIndex("TIME"));
			cursor.close();
			return time;				
		}
		public String getBabyWeight(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String weight= cursor.getString(cursor.getColumnIndex("WEIGHT"));
			cursor.close();
			return weight;				
		}
		public String getBabyHeight(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String height= cursor.getString(cursor.getColumnIndex("HEIGHT"));
			cursor.close();
			return height;				
		}
		public String checkedBabyName(String userID,String name)
		{
			Cursor cursor=dbBaby.query("BABY", null, " USER_ID=? and BABYNAME=?", new String[]{userID,name}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
	        else{
	        	cursor.close();
	        	return "EXISTED";
	        }				
		}
		public void  updateEntryCreateBaby(String babyName,String gender,String date,String time,Double weight,Double height,String image,Integer userId)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("BABYNAME", babyName);
			updatedValues.put("GENDER", gender);
			updatedValues.put("DATE", date);
			updatedValues.put("TIME", time);
			updatedValues.put("WEIGHT", weight);
			updatedValues.put("HEIGHT",height);
			updatedValues.put("IMAGE",image);
			updatedValues.put("USER_ID",userId);
			
	        String where="USER_ID = ?";
		    dbBaby.update("BABY",updatedValues, where, new String[]{userId.toString()});			   
		}		
}

