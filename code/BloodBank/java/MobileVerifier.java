package com.example.root.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by root on 10/4/17.
 */

public class MobileVerifier extends AppCompatActivity
{
    private String username;
    private String password;
    private String name;
    private String mobile;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    private EditText editTextMobile;
    private AlertDialog alertDialog;
    private Button buttonSendOTP;

    private int countMobiles;
    private String randomNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_mobile);

        customizeActionBar();
        customizeStatusBar();
        receiveIntent();
        initializeViews();
        buttonSendOTP.setOnClickListener( new MyListener() );
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(MobileVerifier.this, R.color.blood)) );
        actionBar.setTitle("Verify Mobile");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(MobileVerifier.this, R.color.blood) );
        }
    }

    private void receiveIntent()
    {
        Intent intent = getIntent();
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        name = intent.getStringExtra("labelName");
        country = intent.getStringExtra("labelCountry");
        bloodGroup = intent.getStringExtra("labelBloodGroup");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
    }


    private void initializeViews()
    {
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        buttonSendOTP = (Button) findViewById(R.id.buttonSendOTP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch( item.getItemId() )
        {
            case android.R.id.home :
                this.finish();
                break;
        }

        return true;
    }

    private class MyListener implements View.OnClickListener
    {
        private AttributeCounter mobileNumberCounter;
        private  MobileMessageSender mobileMessageSender;

        @Override
        public void onClick(View v)
        {
            mobile = editTextMobile.getText().toString();
            countMobiles();

            if( countMobiles != 0 )
            {
                Toast.makeText(MobileVerifier.this, "Mobile number already registered!!!", Toast.LENGTH_SHORT).show();
                return;
            }

            sendOTP();
            launchDialog();
        }

        private void countMobiles()
        {
            mobileNumberCounter = new AttributeCounter(MobileVerifier.this);
            countMobiles = mobileNumberCounter.countMobiles(mobile);
        }

        private void generateRandomNumber()
        {
            int max = 1000000;
            int min = 0;
            int number;
            Random random = new Random();

            number = random.nextInt( (max+1)-min ) + min;
            randomNumber = Integer.toString(number);
        }

        private void sendOTP()
        {
            generateRandomNumber();
            mobileMessageSender = new MobileMessageSender(MobileVerifier.this);
            mobileMessageSender.sendMessage(mobile, randomNumber);
        }

        private void launchDialog()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MobileVerifier.this);
            builder.setPositiveButton("Validate", new AlertDialogPosButListener());
            builder.setNegativeButton("Resend", new AlertDialogNegButListener());
            builder.setView(R.layout.mobile_otp_validation);
            builder.setCancelable(true);
            alertDialog = builder.create();
            alertDialog.show();
        }


    }

    private void startEmailVerifier()
    {
        Intent intent = new Intent(MobileVerifier.this, EmailVerifier.class);
        intent.putExtra("labelUsername", username);
        intent.putExtra("labelPassword", password);
        intent.putExtra("labelName", name);
        intent.putExtra("labelMobile", mobile);
        intent.putExtra("labelCountry", country);
        intent.putExtra("labelBloodGroup", bloodGroup);
        intent.putExtra("labelIsDonor", isDonor);
        startActivity(intent);
    }


    private class AlertDialogPosButListener implements DialogInterface.OnClickListener
    {
        private EditText editTextUserOTP;
        private String userOTP;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            editTextUserOTP = (EditText) alertDialog.findViewById(R.id.editTextOTP);
            userOTP = editTextUserOTP.getText().toString();

            if( !randomNumber.equals(userOTP) )
            {
                Toast.makeText(MobileVerifier.this, "Invalid OTP!!!", Toast.LENGTH_SHORT).show();
                buttonSendOTP.setText("Resend OTP");
            }

            else
            {
                Toast.makeText(MobileVerifier.this, "Mobile number Verified!!!", Toast.LENGTH_SHORT).show();
                startEmailVerifier();
            }
        }

    }

    private class AlertDialogNegButListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            alertDialog.dismiss();
            editTextMobile.setText(mobile);
            buttonSendOTP.callOnClick();
        }
    }

}
