package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 2/4/17.
 */

public class ProfileEditHandler extends Profile
{
    private Context context;
    private DataBase dataBase;

    public ProfileEditHandler(Context inpContext)
    {
        context = inpContext;
        dataBase = new DataBase(context);
    }

    protected class NameEditHandler implements View.OnClickListener
    {
        private String commandUpdateName;
        private EditText editTextNewName;

        @Override
        public void onClick( View v )
        {
            final View myView = LayoutInflater.from(context).inflate(R.layout.edit_name,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener()
            {
                String updatedName;

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    initializeCommand();
                    dataBase.executeQuery( commandUpdateName, true );
                    name = updatedName;
                    Toast.makeText(context, "Name updated!!!", Toast.LENGTH_SHORT).show();
                }

                private void initializeCommand()
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    editTextNewName = (EditText) myView.findViewById(R.id.editTextEditName);
                    updatedName = editTextNewName.getText().toString();

                    stringBuilder.append("update user set name='").append(updatedName).append("' where username='")
                            .append(username).append("';");
                    commandUpdateName = stringBuilder.toString();
                }
            });

            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Update Name");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }


}
