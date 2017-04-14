package com.example.root.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by root on 14/4/17.
 */

public class DonorFinderActivity extends AppCompatActivity
{
    private Button buttonFindAllDonors;
    private Button buttonFindDonorsInSameCountry;

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

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.find_donors);

        getDataFromActivity();
        initializeViews();
        handleButtonFindAllDonors();
        handleButtonDonorsInSameCountry();
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
                donorFinder.find(bloodGroup, isPositive, "none", DonorFinderActivity.this);
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
                donorFinder.find(bloodGroup, isPositive, country , DonorFinderActivity.this);
            }
        });
    }


}
