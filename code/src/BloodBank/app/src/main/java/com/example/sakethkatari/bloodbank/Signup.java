package com.example.sakethkatari.bloodbank;

/**
 * Created by root on 28/3/17.
 */

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Signup extends AppCompatActivity
{
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextCountry;
    private EditText editTextBloodgroup;
    private RadioGroup radioGroup;
    private Button buttonSignup;

    private String name;
    private String username;
    private String password;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        customizeActionBar();
        customizeStatusBar();
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

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(Signup.this, R.color.blood)) );
        actionBar.setTitle("Signup");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(Signup.this, R.color.blood) );
        }
    }

    private void initializeViews()
    {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCountry = (EditText) findViewById(R.id.editTextCountry);
        editTextBloodgroup = (EditText) findViewById(R.id.editTextBloodGroup);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupDonorAcceptor);
        buttonSignup = (Button) findViewById(R.id.buttonSignUp);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch( item.getItemId() )
        {
            case android.R.id.home :
                this.finish();
                break;
        }

        return true;
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
        private AttributeCounter attributeCounter;

        public void onClick( View v )
        {
            getUserInput();
            attributeCounter = new AttributeCounter(Signup.this);

            if( attributeCounter.countUsernames(username) == 0 )
            {
                startMobileVerifierActivity();
            }

            else
            {
                Toast.makeText(Signup.this, "Username already exists!!!", Toast.LENGTH_SHORT).show();
            }
        }

        private void getUserInput()
        {
            name = editTextName.getText().toString();
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
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

        private void startMobileVerifierActivity()
        {
            Intent intent = new Intent(Signup.this, MobileVerifier.class);
            intent.putExtra("labelUsername", username);
            intent.putExtra("labelPassword", password);
            intent.putExtra("labelName", name);
            intent.putExtra("labelCountry", country);
            intent.putExtra("labelBloodGroup", bloodGroup);
            intent.putExtra("labelIsDonor", isDonor);
            startActivity(intent);
        }


    }
}