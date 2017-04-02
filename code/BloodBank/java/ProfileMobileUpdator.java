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

public class ProfileMobileUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateMobile;
    private String commandCountMobile;
    private EditText editTextNewMobile;

    private View myView;

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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class MyListener implements DialogInterface.OnClickListener
    {
        private String updatedMobile;
        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private int countMobiles;

        private String toastMessage;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();

            try
            {
                dataBase.executeQuery( commandCountMobile, false );
                resultSet = dataBase.getResultSet();
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countMobiles = resultSet.getInt(1);

            }

            catch (SQLException e)
            {
                e.printStackTrace();
            }

            toastMessage = "Mobile Number already Exists!!!";

            if( countMobiles == 0 )
            {
                dataBase.executeQuery( commandUpdateMobile, true );
                mobile = updatedMobile;
                toastMessage = "Mobile Number Updated!!!";
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();
            editTextNewMobile = (EditText) myView.findViewById(R.id.editTextEditMobile);
            updatedMobile = editTextNewMobile.getText().toString();

            stringBuilder.append("select count(*) from user where mobileNumber='").append(updatedMobile).append("';");
            commandCountMobile = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("update user set mobileNumber='").append(updatedMobile).append("' where username='")
                    .append(username).append("';");
            commandUpdateMobile = stringBuilder.toString();
        }
    }

}
