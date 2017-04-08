package com.example.root.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by root on 29/3/17.
 */

public class Profile extends AppCompatActivity
{
    protected static ImageView imageViewName;
    protected static ImageView imageEditName;
    protected static ImageView imageViewUsername;
    protected static ImageView imageEditUsername;
    protected static ImageView imageEditPassword;
    protected static ImageView imageViewMobile;
    protected static ImageView imageEditMobile;
    protected static ImageView imageViewEmail;
    protected static ImageView imageEditEmail;
    protected static ImageView imageViewCountry;
    protected static ImageView imageEditCountry;
    protected static ImageView imageViewBloodGroup;
    protected static ImageView imageEditBloodGroup;
    protected static ImageView imageViewIsDonor;
    protected static ImageView imageEditIsDonor;

    protected static TextView textViewProfileItemView;

    protected static String name;
    protected static String username;
    protected static String password;
    protected static String mobile;
    protected static String email;
    protected static String country;
    protected static String bloodGroup;
    protected static boolean isDonor;

    private ProfileViewHandler profileViewHandler;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_profile);

        getDataFromParentActivity();
        initializeViews();
        handleImageViews();
        handleImageEdits();
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
        profileViewHandler = new ProfileViewHandler( Profile.this );

        imageViewName.setOnClickListener( profileViewHandler.new NameViewHandler() );
        imageViewUsername.setOnClickListener( profileViewHandler.new UsernameViewHandler() );
        imageViewMobile.setOnClickListener( profileViewHandler.new MobileViewHandler()  );
        imageViewEmail.setOnClickListener( profileViewHandler.new EmailViewHandler() );
        imageViewCountry.setOnClickListener( profileViewHandler.new CountryViewHandler() );
        imageViewBloodGroup.setOnClickListener( profileViewHandler.new BloodGroupViewHandler() );
        imageViewIsDonor.setOnClickListener( profileViewHandler.new IsDonorViewHandler() );
    }

    private void handleImageEdits()
    {
        imageEditName.setOnClickListener( new ProfileNameUpdator(Profile.this) );
        imageEditUsername.setOnClickListener( new ProfileUsernameUpdator(Profile.this) );
        imageEditPassword.setOnClickListener( new ProfilePasswordUpdator(Profile.this) );
        imageEditMobile.setOnClickListener( new ProfileMobileUpdator(Profile.this) );
        imageEditEmail.setOnClickListener( new ProfileEmailUpdator(Profile.this) );
        imageEditCountry.setOnClickListener( new ProfileCountryUpdator(Profile.this) );
        imageEditBloodGroup.setOnClickListener( new ProfileBloodGroupUpdator(Profile.this) );
        imageEditIsDonor.setOnClickListener( new ProfileDonorUpdator(Profile.this) );
    }

}
