package com.example.root.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
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
    private boolean isItDelivered;
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
        private String acknowledgeSent;
        private String acknowledgeDelivered;

        PendingIntent pendingIntentSent;
        PendingIntent pendingIntentDelivered;

        @Override
        public void onClick(View v)
        {
            mobile = editTextMobile.getText().toString();
            isItDelivered = false;
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

        private void initializeAcknowledgementMessages()
        {
            acknowledgeSent = "OTP Sent!!!";
            acknowledgeDelivered = "OTP Delivered";
        }

        private void initializePendingIntents()
        {
            pendingIntentSent = PendingIntent.getBroadcast(MobileVerifier.this, 0, new Intent(acknowledgeSent), 0);
            pendingIntentDelivered = PendingIntent.getBroadcast(MobileVerifier.this, 0, new Intent(acknowledgeDelivered), 0);
        }

        private void registerReceivers()
        {
            registerReceiver( new MyBroadcastReceiverSent(acknowledgeSent), new IntentFilter(acknowledgeSent) );
            registerReceiver( new MyBroadcastReceiverDelivered(acknowledgeDelivered), new IntentFilter(acknowledgeDelivered) );
        }

        private void sendOTP()
        {
            generateRandomNumber();
            initializeAcknowledgementMessages();
            initializePendingIntents();
            registerReceivers();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage( mobile, null, randomNumber, pendingIntentSent, pendingIntentDelivered );
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

    private class MyBroadcastReceiverSent extends BroadcastReceiver
    {
        private String acknowledgeSent;

        public MyBroadcastReceiverSent(String inpStr)
        {
            acknowledgeSent = inpStr;
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if( getResultCode() == Activity.RESULT_OK )
            {
                //Toast.makeText(MobileVerifier.this, acknowledgeSent, Toast.LENGTH_SHORT ).show();
            }

            else if( getResultCode() == SmsManager.RESULT_ERROR_GENERIC_FAILURE )
            {
                //Toast.makeText(MobileVerifier.this, "Generic Failure.", Toast.LENGTH_SHORT ).show();
            }

            else if( getResultCode() == SmsManager.RESULT_ERROR_NO_SERVICE )
            {
                //Toast.makeText(MobileVerifier.this, "No Service.", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private class MyBroadcastReceiverDelivered extends BroadcastReceiver
    {
        private String acknowledgeDelivered;

        public MyBroadcastReceiverDelivered(String inpStr)
        {
            acknowledgeDelivered = inpStr;
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if( getResultCode() == Activity.RESULT_OK )
            {
                //Toast.makeText(MobileVerifier.this, acknowledgeDelivered, Toast.LENGTH_SHORT ).show();
                isItDelivered = true;
            }

            else if( getResultCode() == Activity.RESULT_CANCELED )
            {
                //Toast.makeText(MobileVerifier.this, "OTP not delivered.", Toast.LENGTH_SHORT ).show();
            }
        }
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
                Toast.makeText(MobileVerifier.this, "Mobile Verified!!!", Toast.LENGTH_SHORT).show();
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
