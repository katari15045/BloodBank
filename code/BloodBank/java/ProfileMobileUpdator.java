package com.example.root.home;

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

public class ProfileMobileUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateMobile;
    private String commandUpdateMobileInExtraTable;

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
        private EditText editTextNewMobile;
        private String updatedMobile;
        private AttributeCounter attributeCounter;
        private int countMobiles;

        private String toastMessage;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            attributeCounter = new AttributeCounter(ProfileMobileUpdator.this);
            initializeCommands();
            countMobiles = attributeCounter.countMobiles(updatedMobile);

            toastMessage = "Mobile Number already Exists!!!";

            if( countMobiles == 0 )
            {
                updateMobile();
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
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

        private void updateMobile()
        {
            dataBase.executeQuery( commandUpdateMobile, true );
            dataBase.executeQuery( commandUpdateMobileInExtraTable, true );
            mobile = updatedMobile;
            toastMessage = "Mobile Number Updated!!!";
        }
    }

}
