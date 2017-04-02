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

public class ProfileCountryUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String commandUpdateCountry;
    private EditText editTextNewCountry;

    public ProfileCountryUpdator(Context inpContext)
    {
        context = inpContext;
    }

    public void onClick( View v )
    {
        final View myView = LayoutInflater.from(context).inflate(R.layout.edit_country,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener()
        {
            String updatedCountry;

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dataBase = new DataBase(context);
                initializeCommand();
                dataBase.executeQuery( commandUpdateCountry, true );
                country = updatedCountry;
                Toast.makeText(context, "Country updated!!!", Toast.LENGTH_SHORT).show();
            }

            private void initializeCommand()
            {
                StringBuilder stringBuilder = new StringBuilder();
                editTextNewCountry = (EditText) myView.findViewById(R.id.editTextEditCountry);
                updatedCountry = editTextNewCountry.getText().toString();

                stringBuilder.append("update user set country='").append(updatedCountry).append("' where username='")
                        .append(username).append("';");
                commandUpdateCountry = stringBuilder.toString();
            }
        });

        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Name");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}