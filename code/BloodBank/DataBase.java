package com.example.root.home;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by root on 1/4/17.
 */

public class DataBase
{
    private Context context;

    private String ipAddress;
    private String portNumber;
    private String database;
    private String url;
    private String username;
    private String password;

    private String command;
    private boolean isUpdate;
    private boolean isRead;

    private BackgroundThread backgroundThread;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DataBase(Context inpContext)
    {
        context = inpContext;
    }

    public void executeQuery(String inpCommand, boolean inpIsUpdate)
    {
        command = inpCommand;

        if(inpIsUpdate)
        {
            isUpdate = true;
            isRead = false;
        }

        else
        {
            isRead = true;
            isUpdate = false;
        }

        backgroundThread = new BackgroundThread();

        try
        {
            backgroundThread.execute().get();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet()
    {
        return resultSet;
    }

    private class BackgroundThread extends AsyncTask<String, Void, String>
    {
        private StringBuilder result;

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                initializeServerDetails();

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                if(isRead)
                {
                    resultSet = statement.executeQuery(command);
                }

                else
                {

                    statement.executeUpdate(command);
                }
            }
            catch( ClassNotFoundException e )
            {
                e.printStackTrace();
            }

            catch ( SQLException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        private void initializeServerDetails()
        {
            StringBuilder stringBuilder = new StringBuilder();
            ipAddress = "192.168.56.134";
            portNumber = "3306";
            database = "test_db";

            stringBuilder.append("jdbc:mysql://").append(ipAddress).append(":").append(portNumber).append("/").append(database);
            url = stringBuilder.toString();
            username = "test_user";
            password = "Test_user9977";
        }

        @Override
        protected void onPostExecute(String parameter)
        {
            System.out.println("Completed");
        }
    }

}
