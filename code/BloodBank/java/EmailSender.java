package com.example.root.home;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by root on 13/4/17.
 */

public class EmailSender
{
    private String email;
    private String message;
    private boolean isItSent;

    private Context context;
    private Properties properties;
    private Session session;
    private Message mimeMessage;

    private BackGroundThread backGroundThread;

    public EmailSender(Context inpContext)
    {
        context = inpContext;
    }


    public boolean send(String inpEmail, String inpMessage)
    {
        email = inpEmail;
        message = inpMessage;
        isItSent = false;

        fillProperties();
        startSession();
        backGroundThread = new BackGroundThread();
        backGroundThread.execute();

        return  isItSent;
    }


    private void fillProperties()
    {
        properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
    }

    private void startSession()
    {
        session = Session.getDefaultInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("saketh9977.test@gmail.com", "test_password");
            }
        });
    }

    private class BackGroundThread extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom( new InternetAddress("saketh9977.test@gmail.com") );
                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(email) );
                mimeMessage.setSubject("Blood Bank Email Verification");
                mimeMessage.setContent(message, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            }

            catch(MessagingException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String parameter)
        {
            Toast.makeText(context, "Email sent!!!", Toast.LENGTH_SHORT).show();
            isItSent = true;
        }

    }

}
