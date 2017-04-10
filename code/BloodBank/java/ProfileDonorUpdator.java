package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

    private ExtraTableCreatorCumInserter extraTableCreatorCumInserter;
    private String commandDeleteIsDonorInExtraTable;
    private String commandCountEntries;
    private String commandDropTable;

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
        private boolean updatedIsDonor;
        private String toastMessage;
        private String tableToFree;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            EmptyTableEraser emptyTableEraser = new EmptyTableEraser(tableToFree, context);
            dataBase = new DataBase(context);
            initializeCommand();
            toastMessage = "That's same as the old one!!!";

            if( isDonor != updatedIsDonor )
            {
                dataBase.executeQuery( commandUpdateIsDonor, true );
                dataBase.executeQuery( commandDeleteIsDonorInExtraTable, true );
                emptyTableEraser.start();
                extraTableCreatorCumInserter = new ExtraTableCreatorCumInserter(bloodGroup, updatedIsDonor, username, name, mobile, email, country, context);
                extraTableCreatorCumInserter.start();
                isDonor = updatedIsDonor;

                toastMessage = "Data updated!!!";
            }


            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            getUserInput();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update user set isDonor=").append(updatedIsDonor).append(" where username='")
                    .append(username).append("';");
            commandUpdateIsDonor = stringBuilder.toString();

            if( isDonor != updatedIsDonor )
            {
                tableDecider = new TableDecider();
                tableDecider.decide(bloodGroup, isDonor);
                tableToUpdate = tableDecider.getTable();

                stringBuilder.setLength(0);
                stringBuilder.append( "DELETE FROM " ).append(tableToUpdate).append(" WHERE username='").append(username).append("';");
                commandDeleteIsDonorInExtraTable = stringBuilder.toString();

                tableToFree = tableToUpdate;
            }
        }

        private void getUserInput()
        {
            checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            checkedRadioButton = (RadioButton) myView.findViewById(checkedRadioButtonId);

            if( checkedRadioButton.getText().toString().equals("Donor") )
            {
                updatedIsDonor = true;
            }

            else
            {
                updatedIsDonor = false;
            }

        }
    }

}
