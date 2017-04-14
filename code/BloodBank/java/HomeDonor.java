package com.example.root.home;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by root on 29/3/17.
 */

public class HomeDonor extends AppCompatActivity
{
    private Intent intent;
    private String startActivity;

    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String country;
    private boolean isDonor;
    private String bloodGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        customizeActionBar();
        customizeStatusBar();
        getDataFromActivity();
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(HomeDonor.this, R.color.blood)) );
        actionBar.setTitle("Home");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(HomeDonor.this, R.color.blood) );
        }
    }

    private void getDataFromActivity()
    {
        intent = getIntent();
        startActivity = intent.getStringExtra("labelStartActivity");
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        name = intent.getStringExtra("labelName");
        mobile = intent.getStringExtra("labelMobile");
        email = intent.getStringExtra("labelEmail");
        country = intent.getStringExtra("labelCountry");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
        bloodGroup = intent.getStringExtra("labelBloodGroup");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 1, 1, "Profile");
        menu.add(0, 2, 2, "Logout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case 1:
                handleProfileItem();
                return  true;
            case 2:
                handleLogout();
                return true;
        }

        return true;
    }

    private void handleProfileItem()
    {
        Intent intent = new Intent( HomeDonor.this, Profile.class );
        intent.putExtra("labelStartActivity", "HomeDonor");
        intent.putExtra("labelName", name);
        intent.putExtra("labelUsername", username);
        intent.putExtra("labelPassword", password);
        intent.putExtra("labelMobile", mobile);
        intent.putExtra("labelEmail", email);
        intent.putExtra("labelCountry", country);
        intent.putExtra("labelBloodGroup", bloodGroup);
        intent.putExtra("labelIsDonor", isDonor);
        startActivity(intent);
    }

    private void handleLogout()
    {
        Intent intent = new Intent(HomeDonor.this, Startup.class);
        startActivity(intent);
    }
}
