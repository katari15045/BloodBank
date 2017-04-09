package com.example.root.home;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by root on 9/4/17.
 */

public class EmptyTableEraser
{
    private Context context;
    private String table;
    private DataBase dataBase;
    private String commandCountEntries;
    private  String commandDropTable;

    public EmptyTableEraser(String inpTable, Context inpContext)
    {
        table = inpTable;
        context = inpContext;
    }

    public void start()
    {
        initializeCommands();
        deleteTableIfEmpty();
    }

    private void initializeCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.setLength(0);;
        stringBuilder.append("SELECT COUNT(*) FROM ").append(table).append(";");
        commandCountEntries = stringBuilder.toString();
        Log.d("SAKETH 0 -> ", "commandCountEntries -> " + commandCountEntries);

        stringBuilder.setLength(0);
        stringBuilder.append("DROP TABLE ").append(table).append(";");
        commandDropTable = stringBuilder.toString();
    }


    private void deleteTableIfEmpty()
    {
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        int countEntries = 0;

        try
        {
            dataBase = new DataBase(context);
            dataBase.executeQuery( commandCountEntries, false );
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countEntries = resultSet.getInt(1);

            if( countEntries == 0 )
            {
                dataBase.executeQuery( commandDropTable, true );
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
