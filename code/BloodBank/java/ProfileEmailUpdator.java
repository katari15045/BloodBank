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
    private String commandCountEmail;
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
        private String updatedEmail;
        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private int countEmails;

        private String toastMessage;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();

            try
            {
                dataBase.executeQuery( commandCountEmail, false );
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countEmails = resultSet.getInt(1);

            }

            catch (SQLException e)
            {
                e.printStackTrace();
            }

            toastMessage = "Email already Exists!!!";

            if( countEmails == 0 )
            {
                dataBase.executeQuery( commandUpdateEmail, true );
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

            stringBuilder.append("select count(*) from user where email='").append(updatedEmail).append("';");
            commandCountEmail = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("update user set email='").append(updatedEmail).append("' where username='")
                    .append(username).append("';");
            commandUpdateEmail = stringBuilder.toString();
        }

    }


}
