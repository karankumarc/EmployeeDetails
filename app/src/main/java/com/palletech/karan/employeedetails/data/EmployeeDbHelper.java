package com.palletech.karan.employeedetails.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.palletech.karan.employeedetails.data.EmployeeContract.EmployeeEntry;

/**
 * Created by ADMIN on 9/4/2016.
 */
public class EmployeeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "employee.db";
    private static final int DATABASE_VERSION = 1;

    private static String SQL_CREATE_EMPLOYEE_TABLE = "CREATE TABLE "+EmployeeEntry.TABLE_NAME+"("
            +   EmployeeEntry._ID +" INTEGER PRIMARY KEY,"
            +   EmployeeEntry.COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL,"
            +   EmployeeEntry.COLUMN_EMPLOYEE_DESIGNATION + " TEXT DEFAULT 'Clerk',"
            +   EmployeeEntry.COLUMN_EMPLOYEE_SALARY + " INTEGER DEFAULT 0);";

    private static String SQL_DROP_EMPLOYEE_TABLE = "DROP TABLE "+EmployeeEntry.TABLE_NAME+";";

    public EmployeeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EMPLOYEE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL(SQL_DROP_EMPLOYEE_TABLE);
            onCreate(db);
        }
    }
}
