package com.example.sakethkatari.bloodbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by root on 3/4/17.
 */

public class ProfileMobileUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private EditText editTextUserOTP;
    private DataBase dataBase;
    private String commandUpdateMobile;
    private String commandUpdateMobileInExtraTable;

    private View myView;
    private AlertDialog alertDialogmobile;
    private AlertDialog alertDialogOTP;
    private String randomNumber;
    private String toastMessage;
    private String updatedMobile;

    private MobileMessageSender mobileMessageSender;

    public ProfileMobileUpdator(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick(View v)
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_mobile,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyListener() );

        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Mobile Number");
        alertDialogmobile = builder.create();
        alertDialogmobile.show();
    }

    private void sendOTP()
    {
        generateRandomNumber();
        mobileMessageSender = new MobileMessageSender(context);
        mobileMessageSender.sendMessage(updatedMobile, randomNumber);
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

    private void displayToastMessage()
    {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void launchOTPDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Validate", new AlertDialogPosButListener());
        builder.setNegativeButton("Resend", new AlertDialogNegButListener());
        builder.setView(R.layout.mobile_otp_validation);
        builder.setCancelable(true);
        alertDialogOTP = builder.create();
        alertDialogOTP.show();
    }

    private class MyListener implements DialogInterface.OnClickListener
    {
        private EditText editTextNewMobile;
        private AttributeCounter attributeCounter;
        private int countMobiles;



        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            attributeCounter = new AttributeCounter(context);
            initializeCommands();
            countMobiles = attributeCounter.countMobiles(updatedMobile);
            toastMessage = "Mobile Number already Exists!!!";

            if( countMobiles == 0 )
            {
                sendOTP();
                launchOTPDialog();
            }

            else
            {
                editTextNewMobile.setText("");
                displayToastMessage();
            }
        }

        private void initializeCommands()
        {
            StringBuilder stringBuilder = new StringBuilder();
            editTextNewMobile = (EditText) myView.findViewById(R.id.editTextEditMobile);
            updatedMobile = editTextNewMobile.getText().toString();

            stringBuilder.append("update user set mobileNumber='").append(updatedMobile).append("' where username='")
                    .append(username).append("';");
            commandUpdateMobile = stringBuilder.toString();

            tableDecider = new TableDecider();
            tableDecider.decide(bloodGroup, isDonor);
            tableToUpdate = tableDecider.getTable();

            stringBuilder.setLength(0);
            stringBuilder.append("update ").append(tableToUpdate).append(" set mobileNumber='").append(updatedMobile).append("' where username='")
                    .append(username).append("';");
            commandUpdateMobileInExtraTable = stringBuilder.toString();
        }

    }

    private class AlertDialogPosButListener implements DialogInterface.OnClickListener
    {
        private String userOTP;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            editTextUserOTP = (EditText) alertDialogOTP.findViewById(R.id.editTextOTP);
            userOTP = editTextUserOTP.getText().toString();

            if( !randomNumber.equals(userOTP) )
            {
                toastMessage = "Invalid OTP!!!";
                displayToastMessage();
            }

            else
            {
                alertDialogOTP.dismiss();
                updateMobile();
            }
        }

        private void updateMobile()
        {
            dataBase.executeQuery( commandUpdateMobile, true );
            dataBase.executeQuery( commandUpdateMobileInExtraTable, true );
            mobile = updatedMobile;
            toastMessage = "Mobile Number Updated!!!";
            displayToastMessage();
        }

    }

    private class AlertDialogNegButListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            launchOTPDialog();
            sendOTP();
        }
    }
}
