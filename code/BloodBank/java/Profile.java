package com.example.root.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
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
    private String startActivity;

    protected static String name;
    protected static String username;
    protected static String password;
    protected static String mobile;
    protected static String email;
    protected static String country;
    protected static String bloodGroup;
    protected static boolean isDonor;

    private ProfileViewHandler profileViewHandler;
    protected static TableDecider tableDecider;
    protected static String tableToUpdate;

    @Override
    public void onCreate(Bundle savedInstances)
    {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_profile);

        getDataFromParentActivity();
        initializeViews();
        customizeActionBar();
        customizeStatusBar();
        handleImageViews();
        handleImageEdits();
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
        Log.d("Saketh1", "Profile 1");
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

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(Profile.this, R.color.blood)) );
        actionBar.setTitle("Profile");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(Profile.this, R.color.blood) );
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 1, 1, "Home");
        menu.add(0, 2, 2, "Logout");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case android.R.id.home:
                this.finish();
                return true;
            case 1:
                handleHome();
                return  true;
            case 2:
                handleLogout();
                return true;
        }

        return true;
    }

    private void handleHome()
    {
        Intent intent;

        if(isDonor)
        {
            intent = new Intent(Profile.this, HomeDonor.class);
        }

        else
        {
            intent = new Intent(Profile.this, HomeRecipient.class);
        }

        fillIntent(intent);
        startActivity(intent);
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

    private void handleLogout()
    {
        Intent intent = new Intent(Profile.this, Startup.class);
        intent.putExtra("labelStartActivity", "Profile");
        startActivity(intent);
    }

}
