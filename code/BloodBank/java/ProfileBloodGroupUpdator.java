package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 8/4/17.
 */

public class ProfileBloodGroupUpdator extends Profile implements View.OnClickListener
{
    private Context context;
    private DataBase dataBase;
    private String updatedBloodGroup;
    private String commandUpdateBloodGroup;
    private String commandUpdateIsPositiveExtraTable;
    private String commandDeleteEntryExtraTable;
    protected EditText editTextNewBloodGroup;
    private View myView;

    public ProfileBloodGroupUpdator(Context inpContext)
    {
        context = inpContext;
    }

    public void onClick( View v )
    {
        myView = LayoutInflater.from(context).inflate(R.layout.edit_bloodgroup,null);
        editTextNewBloodGroup = (EditText) myView.findViewById(R.id.editTextEditBloodGroup);
        editTextNewBloodGroup.setOnClickListener( new MyListenerContextMenu() );

        registerForContextMenu(editTextNewBloodGroup);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Update", new MyPositiveListener());
        builder.setView(myView);
        builder.setCancelable(false);
        builder.setTitle("Update Blood Group");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class MyPositiveListener implements DialogInterface.OnClickListener
    {
        TableDecider tableDecider;
        ExtraTableCreatorCumInserter extraTableCreatorCumInserter;
        EmptyTableEraser emptyTableEraser;
        String oldTable;
        String newTable;
        String toastMessage;
        boolean statusSameTable;
        boolean updatedIsPositive;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();

           if( !bloodGroup.equals(updatedBloodGroup) )
           {
               dataBase.executeQuery( commandUpdateBloodGroup, true );

               if( statusSameTable )
               {
                   dataBase.executeQuery( commandUpdateIsPositiveExtraTable, true );
               }

               else
               {
                   dataBase.executeQuery( commandDeleteEntryExtraTable, true );
                   emptyTableEraser = new EmptyTableEraser(oldTable, context);
                   emptyTableEraser.start();

                   extraTableCreatorCumInserter = new ExtraTableCreatorCumInserter(updatedBloodGroup, isDonor, username, name, mobile, email, country, context);
                   extraTableCreatorCumInserter.start();
               }

               toastMessage = "Blood Group updated!!!";
               bloodGroup = updatedBloodGroup;
           }

           else
           {
               toastMessage = "That's same as the old one!!!";
           }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            isItTheSameTable();

            StringBuilder stringBuilder = new StringBuilder();
            IsPositiveDecider isPositiveDecider;

            isPositiveDecider = new IsPositiveDecider(updatedBloodGroup);
            isPositiveDecider.decide();
            updatedIsPositive = isPositiveDecider.getDecision();

            stringBuilder.append("update user set bloodGroup='").append(updatedBloodGroup).append("' where username='")
                    .append(username).append("';");
            commandUpdateBloodGroup = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("UPDATE ").append(oldTable).append(" SET isPositive=").append(updatedIsPositive).append(";");
            commandUpdateIsPositiveExtraTable = stringBuilder.toString();

            stringBuilder.setLength(0);
            Log.d("SAKETH 1 -> ", " Old Table -> " + oldTable);
            Log.d("SAKETH 2 -> ", " New Table -> " + newTable);
            stringBuilder.append("DELETE FROM ").append(oldTable).append(" WHERE username='").append(username).append("';");
            commandDeleteEntryExtraTable = stringBuilder.toString();
        }

        private boolean isItTheSameTable()
        {
            statusSameTable = false;
            tableDecider = new TableDecider( bloodGroup, isDonor );
            tableDecider.decide();
            oldTable = tableDecider.getTable();

            tableDecider = new TableDecider( updatedBloodGroup, isDonor );
            tableDecider.decide();
            newTable = tableDecider.getTable();

            if( oldTable.equals(newTable) )
            {
                statusSameTable = true;
            }

            return statusSameTable;
        }
    }

    private class MyListenerContextMenu implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            openContextMenu(editTextNewBloodGroup);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(context);
        menuInflater.inflate(R.menu.menu_blood_groups, menu);

        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                onContextItemSelected(item);
                return true;
            }
        };

        for (int i = 0, n = menu.size(); i < n; i++)
            menu.getItem(i).setOnMenuItemClickListener(listener);
    }

    public boolean onContextItemSelected(MenuItem menuItem)
    {
        int itemId = menuItem.getItemId();

        if( itemId == R.id.itemAPos )
        {
            updatedBloodGroup = "A +";
        }

        else if( itemId == R.id.itemANeg )
        {
            updatedBloodGroup = "A -";
        }

        else if( itemId == R.id.itemBPos )
        {
            updatedBloodGroup = "B +";
        }

        else if( itemId == R.id.itemBNeg )
        {
            updatedBloodGroup = "B -";
        }

        else if( itemId == R.id.itemABPos )
        {
            updatedBloodGroup = "AB +";
        }

        else if( itemId == R.id.itemABNeg )
        {
            updatedBloodGroup = "AB -";
        }

        else if( itemId == R.id.itemOPos )
        {
            updatedBloodGroup = "O +";
        }

        else if( itemId == R.id.itemONeg )
        {
            updatedBloodGroup = "O -";
        }

        else
        {
            return super.onContextItemSelected(menuItem);
        }

        editTextNewBloodGroup.setText(updatedBloodGroup);
        return true;
    }
}
