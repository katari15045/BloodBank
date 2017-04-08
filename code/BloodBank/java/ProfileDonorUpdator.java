package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by root on 8/4/17.
 */

public class ProfileDonorUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateIsDonor;
    private RadioGroup radioGroup;

    private View myView;

    public ProfileDonorUpdator(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick(View v)
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_isdonor,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyListener() );
        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Who are you?");
        AlertDialog alertDialog = builder.create();
        initializeViews();
        alertDialog.show();
    }

    private void initializeViews()
    {
        radioGroup = (RadioGroup) myView.findViewById(R.id.radioGroupDonorAcceptor);
    }


    private class MyListener implements DialogInterface.OnClickListener
    {
        private int checkedRadioButtonId;
        private RadioButton checkedRadioButton;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();
            dataBase.executeQuery( commandUpdateIsDonor, true );
            Toast.makeText(context, "Data Upadted!!!", Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            getUserInput();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update user set isDonor=").append(isDonor).append(" where username='")
                    .append(username).append("';");
            commandUpdateIsDonor = stringBuilder.toString();
        }

        private void getUserInput()
        {
            checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            checkedRadioButton = (RadioButton) myView.findViewById(checkedRadioButtonId);

            if( checkedRadioButton.getText().toString().equals("Donor") )
            {
                isDonor = true;
            }

            else
            {
                isDonor = false;
            }
        }
    }

}
