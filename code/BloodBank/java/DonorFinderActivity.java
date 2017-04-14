package com.example.root.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by root on 14/4/17.
 */

public class DonorFinderActivity extends AppCompatActivity
{
    private Button buttonFindAllDonors;
    private Button buttonFindDonorsInSameCountry;

    private String startActivity;
    private Intent backupIntent;

    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String country;
    private boolean isDonor;
    private String bloodGroup;
    private boolean isPositive;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.find_donors);

        customizeActionBar();
        customizeStatusBar();
        getDataFromActivity();
        initializeViews();
        handleButtonFindAllDonors();
        handleButtonDonorsInSameCountry();
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(DonorFinderActivity.this, R.color.blood)) );
        actionBar.setTitle("Find Donors");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(DonorFinderActivity.this, R.color.blood) );
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

        backupIntent = new Intent(DonorFinderActivity.this, ResultDisplayer.class);
        fillIntent(backupIntent);
    }

    private void initializeViews()
    {
        buttonFindAllDonors = (Button) findViewById(R.id.buttonFindAllDonors);
        buttonFindDonorsInSameCountry = (Button) findViewById(R.id.buttonFindDonorsInYourCountry);
    }

    private void handleButtonFindAllDonors()
    {
        buttonFindAllDonors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DonorFinder donorFinder = new DonorFinder();
                donorFinder.find(bloodGroup, isPositive, "none", DonorFinderActivity.this, backupIntent);
            }
        });
    }

    private void handleButtonDonorsInSameCountry()
    {
        buttonFindDonorsInSameCountry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DonorFinder donorFinder = new DonorFinder();
                donorFinder.find(bloodGroup, isPositive, country , DonorFinderActivity.this, backupIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 1, 1, "Home");
        menu.add(0, 2, 2, "Profile");
        menu.add(0, 3, 3, "Logout");

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
                handleLogout();
                break;
        }

        return true;
    }

    private void fillIntent(Intent intent)
    {
        intent.putExtra("labelStartActivity", "DonorFinder");
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
            intent = new Intent(DonorFinderActivity.this, HomeDonor.class);
        }

        else
        {
            intent = new Intent(DonorFinderActivity.this, HomeRecipient.class);
        }

        fillIntent(intent);
        startActivity(intent);
    }

    private void handleProfile()
    {
        Intent intent = new Intent( DonorFinderActivity.this, Profile.class );
        fillIntent(intent);
        startActivity(intent);
    }

    private void handleLogout()
    {
        Intent intent = new Intent(DonorFinderActivity.this, Startup.class);
        intent.putExtra("labelStartActivity", "DonorFinder");
        startActivity(intent);
    }
}
