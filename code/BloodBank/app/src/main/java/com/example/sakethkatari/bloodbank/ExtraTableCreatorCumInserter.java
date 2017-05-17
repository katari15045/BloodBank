package com.example.sakethkatari.bloodbank;

import android.content.Context;

import java.sql.Connection;

/**
 * Created by root on 9/4/17.
 */

public class ExtraTableCreatorCumInserter
{
    private String bloodGroup;
    private boolean isDonor;
    private String username;
    private boolean isPositive;
    private String name;
    private String mobile;
    private String email;
    private String country;
    private Context context;

    private TableDecider tableDecider;
    private IsPositiveDecider isPositiveDecider;
    private String targetTable;

    private String commandToCreateTable;
    private String commandToInsert;
    private DataBase dataBase;

    public ExtraTableCreatorCumInserter(String inpBloodGroup, boolean inpIsDonor,String inpUsername, String inpName,
                                        String inpMobile, String inpEmail, String inpCountry , Context inpContext)
    {
        bloodGroup = inpBloodGroup;
        isDonor = inpIsDonor;
        username = inpUsername;
        name = inpName;
        mobile = inpMobile;
        email = inpEmail;
        country = inpCountry;
        context = inpContext;

        dataBase = new DataBase(context);
    }

    public void start()
    {
        isPositiveDecider = new IsPositiveDecider();
        isPositiveDecider.decide(bloodGroup);
        isPositive = isPositiveDecider.getDecision();

        decideTable();
        initializeCommands();

        createTable();
        insertIntoTable();
    }

    private void decideTable()
    {
        tableDecider = new TableDecider();
        tableDecider.decide(bloodGroup, isDonor);
        targetTable = tableDecider.getTable();
    }

    private void initializeCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS ").append(targetTable).append("(username VARCHAR(100), isPositive BOOL NOT NULL, name VARCHAR(100) NOT NULL, mobileNumber VARCHAR(20) NOT NULL, email VARCHAR(100) NOT NULL, country VARCHAR(50) NOT NULL, PRIMARY KEY(username) );");
        commandToCreateTable = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("INSERT INTO ").append(targetTable).append(" VALUES('").append(username).append("',").append(isPositive).append(", '").append(name)
                .append("', '").append(mobile).append("', '").append(email).append("', '").append(country).append("');");
        commandToInsert = stringBuilder.toString();
    }

    private void createTable()
    {
        dataBase.executeQuery( commandToCreateTable, true );
    }

    private void insertIntoTable()
    {
        dataBase.executeQuery( commandToInsert, true );
    }

}
