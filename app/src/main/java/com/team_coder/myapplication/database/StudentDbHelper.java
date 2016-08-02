package com.team_coder.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by junsuk on 16. 7. 28..
 */
public class StudentDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Student.db";

    private static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL)",
            StudentContract.StudentEntry.TABLE_NAME,
            StudentContract.StudentEntry._ID,
            StudentContract.StudentEntry.COLUMN_NAME_NAME);

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertStudent(String name) {
        // DB 작업
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_NAME_NAME, name);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                StudentContract.StudentEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public Cursor getStudents() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentContract.StudentEntry._ID,
                StudentContract.StudentEntry.COLUMN_NAME_NAME
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudentContract.StudentEntry._ID + " DESC"; // ASC or DESC

        // SELECT _id, name FROM student;
        Cursor cursor = db.query(
                StudentContract.StudentEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return cursor;
    }

    public int deleteStudent(long id) {
        return getWritableDatabase()
                .delete(StudentContract.StudentEntry.TABLE_NAME,
                        StudentContract.StudentEntry._ID + "=" + id,
                        null);
    }

    public int updateStudent(long id, String name) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_NAME_NAME, name);

        return getWritableDatabase()
                .update(StudentContract.StudentEntry.TABLE_NAME,
                        values,
                        StudentContract.StudentEntry._ID + "=" + id,
                        null);
    }
}
