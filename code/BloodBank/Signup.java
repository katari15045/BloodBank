package com.example.root.home;

/**
 * Created by root on 28/3/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;

public class Signup extends AppCompatActivity
{
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextMobileNumber;
    private EditText editTextCountry;
    private EditText editTextBloodgroup;
    private RadioGroup radioGroup;
    private Button buttonSignup;

    private String name;
    private String username;
    private String password;
    private String email;
    private String mobileNumber;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeViews();
        registerForContextMenu(editTextBloodgroup);
        buttonSignup.setOnClickListener( new MyListener() );
    }

    private void initializeViews()
    {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMobileNumber = (EditText) findViewById(R.id.editTextMobile);
        editTextCountry = (EditText) findViewById(R.id.editTextCountry);
        editTextBloodgroup = (EditText) findViewById(R.id.editTextBloodGroup);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupDonorAcceptor);
        buttonSignup = (Button) findViewById(R.id.buttonSignUp);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_blood_groups, menu);
    }

    public boolean onContextItemSelected(MenuItem menuItem)
    {
        int itemId = menuItem.getItemId();

        if( itemId == R.id.itemAPos )
        {
            editTextBloodgroup.setText("A +");
            bloodGroup = "A +";
        }

        else if( itemId == R.id.itemANeg )
        {
            editTextBloodgroup.setText("A -");
            bloodGroup = "A -";
        }

        else if( itemId == R.id.itemBPos )
        {
            editTextBloodgroup.setText("B +");
            bloodGroup = "B +";
        }

        else if( itemId == R.id.itemBNeg )
        {
            editTextBloodgroup.setText("B -");
            bloodGroup = "B -";
        }

        else if( itemId == R.id.itemABPos )
        {
            editTextBloodgroup.setText("AB +");
            bloodGroup = "AB +";
        }

        else if( itemId == R.id.itemABNeg )
        {
            editTextBloodgroup.setText("AB -");
            bloodGroup = "AB -";
        }

        else if( itemId == R.id.itemOPos )
        {
            editTextBloodgroup.setText("O +");
            bloodGroup = "O +";
        }

        else if( itemId == R.id.itemONeg )
        {
            editTextBloodgroup.setText("O -");
            bloodGroup = "O -";
        }

        else
        {
            return super.onContextItemSelected(menuItem);
        }

        return true;
    }

    private class MyListener implements View.OnClickListener
    {
        private String commandCreateUserTable;
        private String commandInsertIntoUser;
        private String commandCountUsername;
        private String commandCountMobileNumber;
        private  String commandCountEmail;

        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private DataBase dataBase;
        private String toastMessage;

        private int checkedRadioButtonId;
        private RadioButton checkedRadioButton;

        private int countUserNames;
        private int countMobiles;
        private int countEmails;

        public void onClick( View v )
        {
            getUserInput();
            initializeCommands();
            dataBase = new DataBase(Signup.this);
            sendCommands();
            toastMessage = "Welcome to Blood Bank!!!";

            if( countUserNames == 1 )
            {
                toastMessage = "Username Already Exists!!!";
            }

            else if( countMobiles == 1 )
            {
                toastMessage = "Mobile number already registered!!!";
            }

            else if( countEmails == 1 )
            {
                toastMessage = "Email already registered!!!";
            }

            else
            {
                dataBase.executeQuery(commandInsertIntoUser, true);
            }

            Toast.makeText(Signup.this, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void sendCommands()
        {
            try
            {
                dataBase.executeQuery(commandCreateUserTable, true);

                dataBase.executeQuery(commandCountUsername, false);
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countUserNames = resultSet.getInt(1);

                if(countUserNames == 1)
                {
                    return;
                }

                dataBase.executeQuery(commandCountMobileNumber, false);
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countMobiles = resultSet.getInt(1);

                if(countMobiles == 1)
                {
                    return;
                }

                dataBase.executeQuery(commandCountEmail, false);
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countEmails = resultSet.getInt(1);
            }

            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        private void getUserInput()
        {
            name = editTextName.getText().toString();
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
            email = editTextEmail.getText().toString();
            mobileNumber = editTextMobileNumber.getText().toString();
            country = editTextCountry.getText().toString();

            checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            checkedRadioButton = (RadioButton) findViewById(checkedRadioButtonId);

            if( checkedRadioButton.getText().toString().equals("Donor") )
            {
                isDonor = true;
            }

            else
            {
                isDonor = false;
            }
        }

        private void initializeCommands()
        {
            StringBuilder stringBuilder = new StringBuilder();
            commandCreateUserTable = "CREATE TABLE IF NOT EXISTS user(name VARCHAR(100) NOT NULL, username VARCHAR(100), password VARCHAR(100) NOT NULL, mobileNumber VARCHAR(20) NOT NULL, email VARCHAR(100) NOT NULL, country VARCHAR(50) NOT NULL, isDonor BOOL NOT NULL, bloodGroup VARCHAR(5) NOT NULL, PRIMARY KEY(username) );";

            stringBuilder.append("INSERT INTO user VALUES('").append(name).append("','").append(username).append("','").append(password).append("','").append(mobileNumber).append("','").append(email).append("','").append(country).append("',").append(isDonor).append(",'").append(bloodGroup).append("');");
            commandInsertIntoUser = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("select count(*) from user where username='").append(username).append("';");
            commandCountUsername = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("select count(*) from user where mobileNumber='").append(mobileNumber).append("';");
            commandCountMobileNumber = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("select count(*) from user where email='").append(email).append("';");
            commandCountEmail = stringBuilder.toString();
        }
    }
}