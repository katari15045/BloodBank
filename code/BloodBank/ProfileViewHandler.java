package com.example.root.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by root on 2/4/17.
 */

public class ProfileViewHandler extends Profile
{
    private Context context;

    public ProfileViewHandler(Context inpContext)
    {
        context = inpContext;
    }

    protected class NameViewHandler  implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            textViewProfileItemView.setText(name);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Name");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    protected class UsernameViewHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Username");
            textViewProfileItemView.setText(username);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    protected class MobileViewHandler implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Mobile");
            textViewProfileItemView.setText(mobile);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    protected class EmailViewHandler implements View.OnClickListener
    {
         @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
                textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
                builder.setNegativeButton("Ok", null);
                builder.setView(myView);
                builder.setCancelable(false);
                builder.setTitle("Email");
                textViewProfileItemView.setText(email);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
    }

    protected class CountryViewHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Country");
            textViewProfileItemView.setText(country);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    protected class BloodGroupViewHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view,null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Blood Group");
            textViewProfileItemView.setText(bloodGroup);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    protected class IsDonorViewHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View myView = LayoutInflater.from(context).inflate(R.layout.profile_item_view, null);
            textViewProfileItemView = (TextView) myView.findViewById(R.id.textViewProfileItemView);
            builder.setNegativeButton("Ok", null);
            builder.setView(myView);
            builder.setCancelable(false);
            builder.setTitle("Are you a donor?");
            textViewProfileItemView.setText(Boolean.toString(isDonor));
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
