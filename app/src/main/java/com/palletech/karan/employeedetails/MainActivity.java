package com.palletech.karan.employeedetails;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palletech.karan.employeedetails.data.EmployeeContract.EmployeeEntry;
import com.palletech.karan.employeedetails.data.EmployeeDbHelper;
import com.palletech.karan.employeedetails.model.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare all visible and invisible components

    //Visible components
    EditText empNoEditText, empNameEditText, empSalEditText;
    Button addBtn;
    ListView listView;

    //Invisible components
    MyAdapter myAdapter;
    ArrayList<Employee> employeeArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize visible components
        empNoEditText = (EditText) findViewById(R.id.empNoEditText);
        empNameEditText = (EditText) findViewById(R.id.empNameEditText);
        empSalEditText = (EditText) findViewById(R.id.empSalaryEditText);
        addBtn = (Button) findViewById(R.id.addBtn);
        listView = (ListView) findViewById(R.id.employeeListView);

        //Initialize invisible components
        myAdapter = new MyAdapter();
        employeeArrayList = new ArrayList<Employee>();

        //Establish link between destination and adapter
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
                Employee employee = employeeArrayList.get(position);
                checkbox.setChecked(!checkbox.isChecked());
                employee.setSelected(checkbox.isChecked());
                Toast.makeText(MainActivity.this, ""+employee.getName()+" "+employee.isSelected(), Toast.LENGTH_SHORT).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmployeeDbHelper helper = new EmployeeDbHelper(MainActivity.this);

                ContentValues contentValues= new ContentValues();
                contentValues.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME, "Ayaz");
                contentValues.put(EmployeeEntry.COLUMN_EMPLOYEE_SALARY, 14000);

                // Opening connection to database
                SQLiteDatabase database = helper.getWritableDatabase();

                // Insert data
                database.insert(EmployeeEntry.TABLE_NAME, null, contentValues);

                // Close database connection
                database.close();
                helper.close();

              /*  String number = empNoEditText.getText().toString();
                String name= empNameEditText.getText().toString();
                String salary = empSalEditText.getText().toString();
                Employee employee= new Employee(number, name, salary);
                employeeArrayList.add(employee);
                myAdapter.notifyDataSetChanged();
                empNoEditText.setText("");
                empNameEditText.setText("");
                empSalEditText.setText("");
                empNoEditText.requestFocus();*/
            }
        });

        readFromDatabaseAndUpdateListView();

    }

    private void readFromDatabaseAndUpdateListView() {
        EmployeeDbHelper helper = new EmployeeDbHelper(this);

        // Opens connection
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] projections = {EmployeeEntry._ID, EmployeeEntry.COLUMN_EMPLOYEE_NAME, EmployeeEntry.COLUMN_EMPLOYEE_SALARY};

        String selection = EmployeeEntry._ID + " >  ? AND "+EmployeeEntry._ID+ " < ?";
        String[] selectionArgs = {"1", "5"};

        Cursor c = database.query(EmployeeEntry.TABLE_NAME, projections, selection, selectionArgs, null, null, null);

        if(c.moveToFirst()){
            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                int salary = c.getInt(2);

                Employee e = new Employee(""+id, name, ""+salary);
                employeeArrayList.add(e);

            } while (c.moveToNext());

        }

        myAdapter.notifyDataSetChanged();



    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //Returns the size of the source
            return employeeArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            //Returns item at position
            return employeeArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            //Returns item id at position
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Loading the row.xml
            //View row= View.inflate(getApplicationContext(),R.layout.row, null);
            View row = getLayoutInflater().inflate(R.layout.row, null);

            TextView empNoTextView, empNameTextView, empSalTextView;
            CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkbox);
            empNoTextView = (TextView) row.findViewById(R.id.empNoTextView);
            empNameTextView = (TextView) row.findViewById(R.id.empNameTextView);
            empSalTextView = (TextView) row.findViewById(R.id.empSalTextView);

            // Getting data from the source and filling in row.xml
            Employee employee = employeeArrayList.get(position);
            empNoTextView.setText(employee.getId());
            empNameTextView.setText(employee.getName());
            empSalTextView.setText(employee.getSalary());

            /*checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
                }
            });*/

            //return the row
            return row;
        }
    }
}
