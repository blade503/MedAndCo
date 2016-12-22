package com.example.medandco.medandco;

import android.app.Service;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.IBinder;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import patient.Patient;

public class ServerRequest extends Service {

    //public static List<Patient> myListPatient = ServerRequest.getServerData();

    public ServerRequest() {
    }

    public void onCreate(){
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(getApplicationContext(), "Le service a été initialisé", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    public int onStopCommand(Intent intent, int flags, int startId){
        Toast.makeText(getApplicationContext(), "LE SERVICE NEST PLUS", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
