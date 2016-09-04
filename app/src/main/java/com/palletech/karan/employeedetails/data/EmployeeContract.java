package com.palletech.karan.employeedetails.data;

import android.provider.BaseColumns;

import com.palletech.karan.employeedetails.model.Employee;

/**
 * Created by ADMIN on 9/4/2016.
 */
public final class EmployeeContract {

    private EmployeeContract(){

    }

    public static class EmployeeEntry implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_EMPLOYEE_NAME = "name";
        public static final String COLUMN_EMPLOYEE_SALARY = "salary";
        public static final String COLUMN_EMPLOYEE_DESIGNATION = "designation";
    }

    public static class DepartmentsEntry {

    }

}
