package com.example.root.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private AttributeCounter mobileNumberCounter;
    private  MobileMessageSender mobileMessageSender;
    private String randomNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_mobile);

        receiveIntent();
        initializeViews();
        buttonSendOTP.setOnClickListener( new MyListener() );
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

    private class MyListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            mobile = editTextMobile.getText().toString();
            sendOTP();
            launchDialog();
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
            builder.setCancelable(false);
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
        private int countMobiles;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            editTextUserOTP = (EditText) alertDialog.findViewById(R.id.editTextOTP);
            userOTP = editTextUserOTP.getText().toString();

            if( !randomNumber.equals(userOTP) )
            {
                Toast.makeText(MobileVerifier.this, "Invalid OTP!!!", Toast.LENGTH_SHORT).show();
                buttonSendOTP.setText("Resend OTP");
                return;
            }

            else
            {
                mobileNumberCounter = new AttributeCounter(MobileVerifier.this);
                countMobiles = mobileNumberCounter.countMobiles(mobile);

                if( countMobiles == 0 )
                {
                    Toast.makeText(MobileVerifier.this, "Mobile number Verified!!!", Toast.LENGTH_SHORT).show();
                    startEmailVerifier();
                }

                else
                {
                    Toast.makeText(MobileVerifier.this, "Mobile number already registered!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
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