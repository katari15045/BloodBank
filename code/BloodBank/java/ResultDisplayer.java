package com.example.root.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.result_displayer);

        getDataFromActivity();
        textView = (TextView) findViewById(R.id.textViewResults);
        display();
    }

    private void getDataFromActivity()
    {
        Intent intent = getIntent();
        result = intent.getStringExtra("labelResult");
    }

    public void display()
    {
        textView.setText( result.toString() );
    }

}
