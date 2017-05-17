package com.example.sakethkatari.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 10/4/17.
 */

public class ResultDisplayer extends AppCompatActivity
{
    private TextView textView;
    private String result;

    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String country;
    private String bloodGroup;
    private boolean isDonor;
    private String startActivity;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.result_displayer);

        customizeActionBar();
        customizeStatusBar();
        getDataFromParentActivity();
        textView = (TextView) findViewById(R.id.textViewResults);
        display();
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(ResultDisplayer.this, R.color.blood)) );
        actionBar.setTitle("Results");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(ResultDisplayer.this, R.color.blood) );
        }
    }

    private void getDataFromParentActivity()
    {
        Intent intent = getIntent();

        startActivity = intent.getStringExtra("labelStartActivity");
        name = intent.getStringExtra("labelName");
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        mobile = intent.getStringExtra("labelMobile");
        email = intent.getStringExtra("labelEmail");
        country = intent.getStringExtra("labelCountry");
        bloodGroup = intent.getStringExtra("labelBloodGroup");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
        result = intent.getStringExtra("labelResult");
    }

    public void display()
    {
        if( result.toString().isEmpty() )
        {
            textView.setText("Sorry, no donors available.");
        }

        else
        {
            textView.setText( result.toString() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 1, 1, "Home");
        menu.add(0, 2, 2, "Profile");
        menu.add(0, 3, 3, "Find Donors");
        menu.add(0, 4, 4, "Logout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case android.R.id.home:
                this.finish();
                break;
            case 1:
                handleHome();
                break;
            case 2:
                handleProfile();
                break;
            case 3:
                handleFindDonors();
                break;
            case 4:
                handleLogout();
                break;
        }

        return true;
    }

    private void fillIntent(Intent intent)
    {
        intent.putExtra("labelStartActivity", "Profile");
        intent.putExtra("labelName", name);
        intent.putExtra("labelUsername", username);
        intent.putExtra("labelPassword", password);
        intent.putExtra("labelMobile", mobile);
        intent.putExtra("labelEmail", email);
        intent.putExtra("labelCountry", country);
        intent.putExtra("labelBloodGroup", bloodGroup);
        intent.putExtra("labelIsDonor", isDonor);
    }

    private void handleHome()
    {
        Intent intent;

        if(isDonor)
        {
            intent = new Intent(ResultDisplayer.this, HomeDonor.class);
        }

        else
        {
            intent = new Intent(ResultDisplayer.this, HomeRecipient.class);
        }

        fillIntent(intent);
        startActivity(intent);
    }

    private void handleProfile()
    {
        Intent intent = new Intent( ResultDisplayer.this, Profile.class );
        fillIntent(intent);
        startActivity(intent);
    }

    private void handleFindDonors()
    {
        Intent intent = new Intent(ResultDisplayer.this, DonorFinderActivity.class);
        fillIntent(intent);
        startActivity(intent);
    }

    private void handleLogout()
    {
        Intent intent = new Intent(ResultDisplayer.this, Startup.class);
        intent.putExtra("labelStartActivity", "DonorFinder");
        startActivity(intent);
    }

}
