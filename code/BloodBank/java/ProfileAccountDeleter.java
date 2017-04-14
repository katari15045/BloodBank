package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by root on 14/4/17.
 */

public class ProfileAccountDeleter extends Profile implements View.OnClickListener
{
    private Context context;

    public ProfileAccountDeleter(Context inpContext)
    {
        context = inpContext;
    }

    @Override
    public void onClick(View v)
    {
        launchDialog();
    }

    private void launchDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Proceed", new AlertDialogPosButListener());
        builder.setNegativeButton("Cancel", null);
        builder.setView(R.layout.delete_account);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private class AlertDialogPosButListener implements DialogInterface.OnClickListener
    {
        private String commandDeleteInUserTable;
        private String commandDeleteInExtraTable;
        private String extraTable;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            deleteAccount();
            startNextActivity();
        }

        private void deleteAccount()
        {
            initializeCommands();
            sendCommands();
            Toast.makeText(context, "Account deleted successfully!!!", Toast.LENGTH_SHORT).show();
        }

        private void initializeCommands()
        {
            StringBuilder stringBuilder = new StringBuilder();
            TableDecider tableDecider = new TableDecider();

            stringBuilder.append("DELETE FROM user WHERE username='").append(username).append("';");
            commandDeleteInUserTable = stringBuilder.toString();

            tableDecider.decide(bloodGroup, isDonor);
            extraTable = tableDecider.getTable();
            stringBuilder.setLength(0);
            stringBuilder.append("DELETE FROM ").append(extraTable).append(" WHERE username='")
                    .append(username).append("';");
            commandDeleteInExtraTable = stringBuilder.toString();
        }

        private void sendCommands()
        {
            DataBase dataBase = new DataBase(context);
            dataBase.executeQuery(commandDeleteInUserTable, true);
            dataBase.executeQuery(commandDeleteInExtraTable, true);
            EmptyTableEraser emptyTableEraser = new EmptyTableEraser(extraTable, context);
            emptyTableEraser.start();
        }

        private void startNextActivity()
        {
            Intent intent = new Intent(context, Startup.class);
            context.startActivity(intent);
        }

    }
}
