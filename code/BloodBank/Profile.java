package com.example.root.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.root.home.R.layout.abc_action_bar_up_container;
import static com.example.root.home.R.layout.profile_item_view;

/**
 * Created by root on 29/3/17.
 */

public class Profile extends AppCompatActivity
{
    private ImageView imageViewName;
    private ImageView imageEditName;
    private ImageView imageViewUsername;
    private ImageView imageEditUsername;
    private ImageView imageEditPassword;
    private ImageView imageViewMobile;
    private ImageView imageEditMobile;
    private ImageView imageViewEmail;
    private ImageView imageEditEmail;
    private ImageView imageViewCountry;
    private ImageView imageEditCountry;
    private ImageView imageViewBloodGroup;
    private ImageView imageEditBloodGroup;
    private ImageView imageViewIsDonor;
    private ImageView imageEditIsDonor;

    private TextView textViewProfileItemView;

    private String name;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    private DataBase dataBase;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_profile);

        getDataFromParentActivity();
        initializeViews();
        handleImageViews();
    }

    private void getDataFromParentActivity()
    {
        Intent intent = getIntent();

        name = intent.getStringExtra("labelName");
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        mobile = intent.getStringExtra("labelMobile");
        email = intent.getStringExtra("labelEmail");
        country = intent.getStringExtra("labelCountry");
        bloodGroup = intent.getStringExtra("labelBloodGroup");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
    }

    private void initializeViews()
    {
        imageViewName = (ImageView) findViewById(R.id.imageNameView);
        imageEditName = (ImageView) findViewById(R.id.imageNameEdit);
        imageViewUsername = (ImageView) findViewById(R.id.imageUsernameView);
        imageEditUsername = (ImageView) findViewById(R.id.imageUsernameEdit);
        imageEditPassword = (ImageView) findViewById(R.id.imagePasswordEdit);
        imageViewMobile = (ImageView) findViewById(R.id.imageMobileView);
        imageEditMobile = (ImageView) findViewById(R.id.imageMobileEdit);
        imageViewEmail = (ImageView) findViewById(R.id.imageEmailView);
        imageEditEmail = (ImageView) findViewById(R.id.imageEmailEdit);
        imageViewCountry = (ImageView) findViewById(R.id.imageCountryView);
        imageEditCountry = (ImageView) findViewById(R.id.imageCountryEdit);
        imageViewBloodGroup = (ImageView) findViewById(R.id.imageBloodGroupView);
        imageEditBloodGroup = (ImageView) findViewById(R.id.imageBloodGroupEdit);
        imageViewIsDonor = (ImageView) findViewById(R.id.imageIsDonorView);
        imageEditIsDonor = (ImageView) findViewById(R.id.imageIsDonorEdit);

    }

    private void handleImageViews()
    {
        dataBase = new DataBase(Profile.this);

        handleNameView();
        handleUsernameView();
        handleMobileView();
        handleEmailView();
        handleCountryView();
        handleBloodGroupView();
        handleIsDonorView();
    }

    private void handleNameView()
    {
        imageViewName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Name");
                textViewProfileItemView.setText(name);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleUsernameView()
    {
        imageViewUsername.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Username");
                textViewProfileItemView.setText(username);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleMobileView()
    {
        imageViewMobile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Mobile");
                textViewProfileItemView.setText(mobile);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleEmailView()
    {
        imageViewEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Email");
                textViewProfileItemView.setText(email);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleCountryView()
    {
        imageViewCountry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Country");
                textViewProfileItemView.setText(country);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleBloodGroupView()
    {
        imageViewBloodGroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Blood Group");
                textViewProfileItemView.setText(bloodGroup);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void handleIsDonorView()
    {
        imageViewIsDonor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View myView = LayoutInflater.from(Profile.this).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Are you a donor?");
                textViewProfileItemView.setText( Boolean.toString(isDonor) );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
