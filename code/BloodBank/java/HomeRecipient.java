package com.example.root.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by root on 10/4/17.
 */

public class HomeRecipient extends AppCompatActivity
{
    private Intent intent;

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
        setContentView(R.layout.activity_recipient_home);

        getDataFromActivity();
    }

    private void getDataFromActivity()
    {
        intent = getIntent();
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipient_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.itemProfile:
                handleProfileItem();
                break;
            case R.id.itemFindDonors:
                handleFindDonors();
                break;
        }

        return true;
    }

    private void handleProfileItem()
    {
        Intent intent = new Intent( HomeRecipient.this, Profile.class );
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

    private void handleFindDonors()
    {
        Toast.makeText( HomeRecipient.this, "Welcome Recipient!!!", Toast.LENGTH_SHORT ).show();
    }
}
