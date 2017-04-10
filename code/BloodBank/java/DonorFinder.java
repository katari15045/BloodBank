package com.example.root.home;

import android.content.Context;
import android.widget.Toast;

import java.util.LinkedHashSet;

/**
 * Created by root on 10/4/17.
 */

public class DonorFinder
{
    private String bloodGroup;
    private String country;
    private Context context;

    private TargetTableFinder targetTableFinder;
    private LinkedHashSet<String> targetTableSet;

    public void find(String inpBloodGroup, String inpCountry, Context inpContext)
    {
        bloodGroup = inpBloodGroup;
        country = inpCountry;
        context = inpContext;

        findTargetTables();
    }

    private void findTargetTables()
    {
        targetTableFinder = new TargetTableFinder();
        targetTableFinder.find(bloodGroup);
        targetTableSet = targetTableFinder.getTables();

        Toast.makeText(context, targetTableSet.toString(), Toast.LENGTH_LONG).show();
    }

}
