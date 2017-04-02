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
 * Created by root on 2/4/17.
 */

public class ProfileUsernameUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateUsername;
    private String commandCountUsername;
    private EditText editTextNewtUsername;

    private View myView;

    public ProfileUsernameUpdator(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick(View v)
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_username,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyListener() );

        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Username");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class MyListener implements DialogInterface.OnClickListener
    {
        private String updatedUsername;
        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private int countUsernames;

        private String toastMessage;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();

            try
            {
                dataBase.executeQuery( commandCountUsername, false );
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countUsernames = resultSet.getInt(1);

            }

            catch (SQLException e)
            {
                e.printStackTrace();
            }

            toastMessage = "Username already Exists!!!";

            if( countUsernames == 0 )
            {
                dataBase.executeQuery( commandUpdateUsername, true );
                username = updatedUsername;
                toastMessage = "Username Updated!!!";
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();
            editTextNewtUsername = (EditText) myView.findViewById(R.id.editTextEditUsername);
            updatedUsername = editTextNewtUsername.getText().toString();

            stringBuilder.append("select count(*) from user where username='").append(updatedUsername).append("';");
            commandCountUsername = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("update user set username='").append(updatedUsername).append("' where username='")
                    .append(username).append("';");
            commandUpdateUsername = stringBuilder.toString();
        }
    }


}
