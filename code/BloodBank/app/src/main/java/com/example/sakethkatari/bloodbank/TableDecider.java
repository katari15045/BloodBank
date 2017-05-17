package com.example.sakethkatari.bloodbank;


/**
 * Created by root on 9/4/17.
 */

public class TableDecider
{
    private StringBuilder tableToUpdate;
    private String bloodGroup;
    private boolean isDonor;

    public void decide(String inpBloodGroup, boolean inpIsdonor)
    {
        bloodGroup = inpBloodGroup;
        isDonor = inpIsdonor;

        decideLHS();
        decideRHS();
    }

    private void decideLHS()
    {
        tableToUpdate = new StringBuilder();

        if (bloodGroup.equals("A +") || bloodGroup.equals("A -"))
        {
            tableToUpdate.append("a");
        }

        else if (bloodGroup.equals("B +") || bloodGroup.equals("B -"))
        {
            tableToUpdate.append("b");
        }

        else if (bloodGroup.equals("O +") || bloodGroup.equals("O -"))
        {
            tableToUpdate.append("o");
        }

        else
        {
            tableToUpdate.append("ab");
        }
    }

    private void decideRHS()
    {
        if (isDonor)
        {
            tableToUpdate.append("Donor");
        }

        else
        {
            tableToUpdate.append("Acceptor");
        }
    }

    public String getTable()
    {
        return tableToUpdate.toString();
    }

}
