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
 * Created by root on 29/3/17.
 */

public class Home extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.itemProfile:
                handleProfileItem();
                return  true;
        }

        return true;
    }

    private void handleProfileItem()
    {
        Intent intent = new Intent( Home.this, Profile.class );
        startActivity(intent);
    }
}
