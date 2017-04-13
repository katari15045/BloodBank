package com.example.root.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by root on 11/4/17.
 */

public class EmailVerifier extends AppCompatActivity
{
    private EditText editTextEmail;
    private Button buttonVerifyEmail;
    private AlertDialog alertDialog;

    private String randomNumber;

    private String username;
    private String password;
    private String name;
    private String mobile;
    private String email;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    private AttributeCounter attributeCounter;
    private EmailSender emailSender;

    private String commandCreateUserTable;
    private String commandInsertIntoUser;

    @Override
    public void onCreate(Bundle state)
    {
        super.onCreate(state);
        setContentView(R.layout.email_verifier);

        receiveIntent();
        initializeViews();
        buttonVerifyEmail.setOnClickListener( new VerifyEmailListener() );
    }

    private void receiveIntent()
    {
        Intent intent = getIntent();
        username = intent.getStringExtra("labelUsername");
        password = intent.getStringExtra("labelPassword");
        name = intent.getStringExtra("labelName");
        mobile = intent.getStringExtra("labelMobile");
        country = intent.getStringExtra("labelCountry");
        bloodGroup = intent.getStringExtra("labelBloodGroup");
        isDonor = intent.getExtras().getBoolean("labelIsDonor");
    }

    private void initializeViews()
    {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonVerifyEmail = (Button) findViewById(R.id.buttonVerifyEmail);
    }

    private class VerifyEmailListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            email = editTextEmail.getText().toString();
            sendEmail();
            launchDialog();
        }

        private void sendEmail()
        {
            generateRandomNumber();
            emailSender = new EmailSender(EmailVerifier.this);
            emailSender.send(email, randomNumber);
        }

        private void launchDialog()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(EmailVerifier.this);
            builder.setPositiveButton("Validate", new AlertDialogPosButListener());
            builder.setNegativeButton("Resend", new AlertDialogNegButListener());
            builder.setView(R.layout.email_otp_validation);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
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
    }


    private class AlertDialogPosButListener implements DialogInterface.OnClickListener
    {
        private EditText editTextUserCode;
        private String userCode;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            editTextUserCode = (EditText) alertDialog.findViewById(R.id.editTextVerificationCode);
            userCode = editTextUserCode.getText().toString();

            if( !randomNumber.equals(userCode) )
            {
                Toast.makeText(EmailVerifier.this, "Invalid Code!!!", Toast.LENGTH_SHORT).show();
                buttonVerifyEmail.setText("Resend Code");
            }

            else
            {
                attributeCounter = new AttributeCounter(EmailVerifier.this);

                if( attributeCounter.countEmails(email) == 0 )
                {
                    Toast.makeText(EmailVerifier.this, "Email Verified!!!", Toast.LENGTH_SHORT).show();
                    storeData();
                    startNextActivity();
                }

                else
                {
                    Toast.makeText(EmailVerifier.this, "Email already registered!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        private void storeData()
        {
            DataBase dataBase = new DataBase(EmailVerifier.this);
            ExtraTableCreatorCumInserter extraTableCreatorCumInserter = new ExtraTableCreatorCumInserter(bloodGroup, isDonor, username,
                    name, mobile, email, country, EmailVerifier.this);

            initializeCommands();
            dataBase.executeQuery(commandCreateUserTable, true);
            dataBase.executeQuery(commandInsertIntoUser, true);
            extraTableCreatorCumInserter.start();

        }

        private void initializeCommands()
        {
            StringBuilder stringBuilder = new StringBuilder();
            commandCreateUserTable = "CREATE TABLE IF NOT EXISTS user(name VARCHAR(100) NOT NULL, username VARCHAR(100), password VARCHAR(100) NOT NULL, mobileNumber VARCHAR(20) NOT NULL, email VARCHAR(100) NOT NULL, country VARCHAR(50) NOT NULL, isDonor BOOL NOT NULL, bloodGroup VARCHAR(5) NOT NULL, PRIMARY KEY(username) );";

            stringBuilder.append("INSERT INTO user VALUES('").append(name).append("','").append(username).append("','").append(password).append("','").append(mobile)
                                .append("','").append(email).append("','").append(country).append("',").append(isDonor).append(",'").append(bloodGroup).append("');");
            commandInsertIntoUser = stringBuilder.toString();
        }


        private void startNextActivity()
        {
            Intent intent;

            if(isDonor)
            {
                intent = new Intent(EmailVerifier.this, HomeDonor.class);
            }

            else
            {
                intent = new Intent(EmailVerifier.this, HomeRecipient.class);
            }

            intent.putExtra("labelUsername", username);
            intent.putExtra("labelPassword", password);
            intent.putExtra("labelName", name);
            intent.putExtra("labelMobile", mobile);
            intent.putExtra("labelEmail", email);
            intent.putExtra("labelCountry", country);
            intent.putExtra("labelBloodGroup", bloodGroup);
            intent.putExtra("labelIsDonor", isDonor);
            startActivity(intent);
        }
    }

    private class AlertDialogNegButListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            alertDialog.dismiss();
            editTextEmail.setText(email);
            buttonVerifyEmail.callOnClick();
        }
    }

}
