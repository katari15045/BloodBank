package com.example.root.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by root on 10/4/17.
 */

public class HomeRecipient extends AppCompatActivity
{
    private TextView textViewRecipient;
    private String startActivity;

    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String country;
    private boolean isDonor;
    private String bloodGroup;
    private boolean isPositive;

    private DonorFinder donorFinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_home);

        customizeActionBar();
        customizeStatusBar();
        textViewRecipient = (TextView) findViewById(R.id.textViewRecipient);
        getDataFromActivity();
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(HomeRecipient.this, R.color.blood)) );
        actionBar.setTitle("Home");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(HomeRecipient.this, R.color.blood) );
        }
    }

    private void getDataFromActivity()
    {
        Intent intent = getIntent();
        startActivity = intent.getStringExtra("labelStartActivity");
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        name = intent.getStringExtra("labelName");
        mobile = intent.getStringExtra("labelMobile");
        email = intent.getStringExtra("labelEmail");
        country = intent.getStringExtra("labelCountry");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
        bloodGroup = intent.getStringExtra("labelBloodGroup");

        IsPositiveDecider isPositiveDecider = new IsPositiveDecider();
        isPositiveDecider.decide(bloodGroup);
        isPositive = isPositiveDecider.getDecision();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 1, 1, "Profile");
        menu.add(0, 2, 2, "Find Donors");
        menu.add(0, 3, 3, "Logout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case 1:
                handleProfileItem();
                break;
            case 2:
                handleFindDonors();
                break;
            case 3:
                handleLogout();
                break;
        }

        return true;
    }

    private void handleProfileItem()
    {
        Intent intent = new Intent( HomeRecipient.this, Profile.class );
        fillIntent(intent);
        startActivity(intent);
    }

    private void handleFindDonors()
    {
        Intent intent = new Intent(HomeRecipient.this, DonorFinderActivity.class);
        fillIntent(intent);
        startActivity(intent);
    }

    private void handleLogout()
    {
        Intent intent = new Intent(HomeRecipient.this, Startup.class);
        startActivity(intent);
    }

    private void fillIntent(Intent intent)
    {
        intent.putExtra("labelStartActivity", "HomeRecipient");
        intent.putExtra("labelName", name);
        intent.putExtra("labelUsername", username);
        intent.putExtra("labelPassword", password);
        intent.putExtra("labelMobile", mobile);
        intent.putExtra("labelEmail", email);
        intent.putExtra("labelCountry", country);
        intent.putExtra("labelBloodGroup", bloodGroup);
        intent.putExtra("labelIsDonor", isDonor);
    }
}
