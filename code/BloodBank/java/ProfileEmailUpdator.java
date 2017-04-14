package com.example.root.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by root on 3/4/17.
 */

public class ProfileEmailUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateEmail;
    private String commandUpdateEmailInExtraTable;
    private EditText editTextNewEmail;

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

    private class MyListener implements DialogInterface.OnClickListener
    {
        private AttributeCounter attributeCounter;
        private String updatedEmail;
        private int countEmails;

        private String toastMessage;

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
                dataBase.executeQuery( commandUpdateEmail, true );
                dataBase.executeQuery( commandUpdateEmailInExtraTable, true );
                email = updatedEmail;
                toastMessage = "Email Updated!!!";
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
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


}
