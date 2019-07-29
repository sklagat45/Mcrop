package com.sklagat46.mcrop.views;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BackgroundWorker extends AsyncTask<String,Void,Void> {
    Context context;
    BackgroundWorker(Context ctx){
        context =ctx;

    }
    @Override
    protected Void doInBackground(String... params) {
        String type =params[0];
        String login_url ="http://10.0.2.2/login.php";
        if (type.equals("login"))
        {
            try {
                URL url =new URL(login_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
    }

}

