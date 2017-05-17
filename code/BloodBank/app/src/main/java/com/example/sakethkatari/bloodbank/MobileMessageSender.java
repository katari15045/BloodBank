package com.example.sakethkatari.bloodbank;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by root on 13/4/17.
 */

class MobileMessageSender
{
    private Context context;
    private boolean isItSent;

    private PendingIntent pendingIntentSent;
    private PendingIntent pendingIntentDelivered;

    MobileMessageSender(Context inpContext)
    {
        context = inpContext;
    }

    boolean sendMessage(String inpMobile, String inpMessage)
    {
        String mobile;
        String message;

        mobile = inpMobile;
        message = inpMessage;
        isItSent = false;
        initializePendingIntents();
        registerReceivers();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage( mobile, null, message, pendingIntentSent, pendingIntentDelivered );

        return  isItSent;
    }

    private void initializePendingIntents()
    {
        pendingIntentSent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        pendingIntentDelivered = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
    }

    private void registerReceivers()
    {
        context.registerReceiver( new MyBroadcastReceiverSent(), new IntentFilter() );
        context.registerReceiver( new MyBroadcastReceiverDelivered(), new IntentFilter() );
    }

    private class MyBroadcastReceiverSent extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if( getResultCode() == Activity.RESULT_OK )
            {
                Toast.makeText(context, "Message Sent!!!", Toast.LENGTH_SHORT ).show();
                isItSent = true;
            }

            else if( getResultCode() == SmsManager.RESULT_ERROR_GENERIC_FAILURE )
            {
                Toast.makeText(context, "Generic Failure.", Toast.LENGTH_SHORT ).show();
            }

            else if( getResultCode() == SmsManager.RESULT_ERROR_NO_SERVICE )
            {
                Toast.makeText(context, "No Service.", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private class MyBroadcastReceiverDelivered extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if( getResultCode() == Activity.RESULT_OK )
            {
                //Toast.makeText(context, "Message Delivered!!!", Toast.LENGTH_SHORT ).show();
            }

            else if( getResultCode() == Activity.RESULT_CANCELED )
            {
                //Toast.makeText(context, "OTP not delivered.", Toast.LENGTH_SHORT ).show();
            }
        }
    }

}
