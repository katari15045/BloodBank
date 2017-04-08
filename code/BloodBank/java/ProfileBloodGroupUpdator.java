package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private String commandUpdateBloodGroup;
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
        String updatedBloodGroup;

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dataBase = new DataBase(context);
            initializeCommand();
            dataBase.executeQuery( commandUpdateBloodGroup, true );
            Toast.makeText(context, "Blood Group updated!!!", Toast.LENGTH_SHORT).show();
        }

        private void initializeCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();
            updatedBloodGroup = bloodGroup;
            stringBuilder.append("update user set bloodGroup='").append(updatedBloodGroup).append("' where username='")
                    .append(username).append("';");
            commandUpdateBloodGroup = stringBuilder.toString();
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
            bloodGroup = "A +";
        }

        else if( itemId == R.id.itemANeg )
        {
            bloodGroup = "A -";
        }

        else if( itemId == R.id.itemBPos )
        {
            bloodGroup = "B +";
        }

        else if( itemId == R.id.itemBNeg )
        {
            bloodGroup = "B -";
        }

        else if( itemId == R.id.itemABPos )
        {
            bloodGroup = "AB +";
        }

        else if( itemId == R.id.itemABNeg )
        {
            bloodGroup = "AB -";
        }

        else if( itemId == R.id.itemOPos )
        {
            bloodGroup = "O +";
        }

        else if( itemId == R.id.itemONeg )
        {
            bloodGroup = "O -";
        }

        else
        {
            return super.onContextItemSelected(menuItem);
        }

        editTextNewBloodGroup.setText(bloodGroup);
        return true;
    }
}
