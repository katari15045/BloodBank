package com.example.root.home;


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

public class ProfileEmailUpdator extends Profile implements View.OnClickListener
{
    private DataBase dataBase;
    private String commandUpdateEmail;
    private String commandUpdateEmailInExtraTable;
    private String randomNumber;
    private String toastMessage;
    private String updatedEmail;

    private Context context;
    private AlertDialog alertDialogVerCode;
    private View myView;

    public ProfileEmailUpdator(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick(View v)
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_email,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyListener() );

        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Email");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sendEmail()
    {
        generateRandomNumber();
        EmailSender emailSender = new EmailSender(context);
        emailSender.send(updatedEmail, randomNumber);
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

    private void launchDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Validate", new AlertDialogPosButListener());
        builder.setNegativeButton("Resend", new AlertDialogNegButListener());
        builder.setView(R.layout.email_otp_validation);
        builder.setCancelable(true);
        alertDialogVerCode = builder.create();
        alertDialogVerCode.show();
    }

    private void displayToast()
    {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private class MyListener implements DialogInterface.OnClickListener
    {
        private AttributeCounter attributeCounter;
        private EditText editTextNewEmail;
        private int countEmails;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            attributeCounter = new AttributeCounter(context);
            initializeCommand();
            countEmails = attributeCounter.countEmails(updatedEmail);
            toastMessage = "Email already Exists!!!";

            if( countEmails == 0 )
            {
                sendEmail();
                launchDialog();
            }

            else
            {
                displayToast();
            }
        }

        private void initializeCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();
            editTextNewEmail = (EditText) myView.findViewById(R.id.editTextEditEmail);
            updatedEmail = editTextNewEmail.getText().toString();

            stringBuilder.append("update user set email='").append(updatedEmail).append("' where username='")
                    .append(username).append("';");
            commandUpdateEmail = stringBuilder.toString();

            tableDecider = new TableDecider();
            tableDecider.decide(bloodGroup, isDonor);
            tableToUpdate = tableDecider.getTable();

            stringBuilder.setLength(0);
            stringBuilder.append("update ").append(tableToUpdate).append(" set email='").append(updatedEmail).append("' where username='")
                    .append(username).append("';");
            commandUpdateEmailInExtraTable = stringBuilder.toString();
        }
    }

    private class AlertDialogPosButListener implements DialogInterface.OnClickListener
    {
        private EditText editTextUserCode;
        private String userCode;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            editTextUserCode = (EditText) alertDialogVerCode.findViewById(R.id.editTextVerificationCode);
            userCode = editTextUserCode.getText().toString();

            if( !randomNumber.equals(userCode) )
            {
                toastMessage = "Invalid Code!!!";
                displayToast();
            }

            else
            {
                updateEmail();
                toastMessage = "Email Updated!!!";
                displayToast();
            }
        }

        private void updateEmail()
        {
            dataBase.executeQuery( commandUpdateEmail, true );
            dataBase.executeQuery( commandUpdateEmailInExtraTable, true );
            email = updatedEmail;
            toastMessage = "Email Updated!!!";
        }
    }

    private class AlertDialogNegButListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            sendEmail();
            launchDialog();
        }
    }
}
