package com.example.murat.benimbebegim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "user.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String DATABASE_CREATE = "create table "+"USERS"+
		                             "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,NAME  text,SURNAME  text,EMAIL text,PASSWORD text); ";
		// Variable to hold the database instance
		public  SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  LoginDataBaseAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public void close() 
		{
			db.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}

		public void insertEntry(String userName,String name,String surname,String email,String password)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("USERNAME", userName);
			newValues.put("NAME", name);
			newValues.put("SURNAME", surname);
			newValues.put("EMAIL", email);
			newValues.put("PASSWORD",password);
			
			
			// Insert the row into your table
			db.insert("USERS", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		public int deleteEntry(String UserName)
		{
			//String id=String.valueOf(ID);
		    String where="USERNAME=?";
		    int numberOFEntriesDeleted= db.delete("USERS", where, new String[]{UserName}) ;
	       // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}
		public String getSinlgeEntry(String email)
		{
			Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{email}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
			cursor.close();
			return password;				
		}
		public String getUserId(String user)
		{
			Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{user}, null, null, null);

			//Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String id= cursor.getString(cursor.getColumnIndex("ID"));
			cursor.close();
			return id;				
		}
		public String checkedEmail(String email)
		{
			Cursor cursor=db.query("USERS", null, " EMAIL=?", new String[]{email}, null, null, null);

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
		public String checkedUserName(String userName)
		{
			Cursor cursor=db.query("USERS", null, " USERNAME=?", new String[]{userName}, null, null, null);

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
		public void  updateEntry(String userName,String name,String surname,String email,String password)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("USERNAME", userName);
			updatedValues.put("NAME", name);
			updatedValues.put("SURNAME", surname);
			updatedValues.put("EMAIL", email);
			updatedValues.put("PASSWORD",password);
			
	        String where="USERNAME = ?";
		    db.update("USERS",updatedValues, where, new String[]{userName});			   
		}		
}

