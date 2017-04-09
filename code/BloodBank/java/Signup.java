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

    private SignupValidator signupValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeViews();

        editTextBloodgroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openContextMenu(editTextBloodgroup);
            }
        });

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
        private int checkedRadioButtonId;
        private RadioButton checkedRadioButton;


        public void onClick( View v )
        {
            getUserInput();
            signupValidator = new SignupValidator(Signup.this, name, username, password, email, mobileNumber, country, bloodGroup, isDonor);
            signupValidator.validate();
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
    }
}