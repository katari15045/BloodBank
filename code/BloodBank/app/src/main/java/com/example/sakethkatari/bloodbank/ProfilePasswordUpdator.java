package com.example.sakethkatari.bloodbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 3/4/17.
 */

public class ProfilePasswordUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdatePassword;
    private EditText editTextOldPass;
    private EditText editTextNewPass;

    private View myView;

    public ProfilePasswordUpdator(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick( View v )
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_password,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyListener() );
        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Password");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class MyListener implements DialogInterface.OnClickListener
    {
        private String oldPassword;
        private String updatedPassword;
        private String toastMessage;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();
            toastMessage = "Incorrect Old Password!!!";

            if( oldPassword.equals(password) )
            {
                dataBase.executeQuery( commandUpdatePassword, true );
                password = updatedPassword;
                toastMessage = "Password Updated!!!";
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();
            editTextOldPass = (EditText) myView.findViewById(R.id.editTextOldPassword);
            editTextNewPass = (EditText) myView.findViewById(R.id.editTextNewPassword);
            oldPassword = editTextOldPass.getText().toString();
            updatedPassword = editTextNewPass.getText().toString();

            stringBuilder.append("update user set password='").append(updatedPassword).append("' where username='")
                    .append(username).append("';");
            commandUpdatePassword = stringBuilder.toString();
        }
    }
}
