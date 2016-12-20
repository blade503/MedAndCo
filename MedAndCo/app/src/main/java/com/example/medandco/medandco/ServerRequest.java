package com.example.medandco.medandco;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import patient.Patient;

public class ServerRequest extends Service {

    public static List<Patient> myListPatient  = genererPatients();

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

    private static List<Patient> genererPatients(){
        List<Patient> tes = new ArrayList<Patient>();
        tes.add(new Patient("Aurélien", "Chapin"));
        tes.add(new Patient("Alexandre", "Wetzler"));
        tes.add(new Patient("Anthony", "Kavanagh"));
        tes.add(new Patient("Jiahui", "papapa"));
        tes.add(new Patient("Emmanuelle", "Fouchere"));
        tes.add(new Patient("Nicolas", "Hulot"));
        tes.add(new Patient("Axel", "Galliot"));
        tes.add(new Patient("Nicolas", "Dubois"));
        tes.add(new Patient("Etienne ", "Wetzler"));
        tes.add(new Patient("Jiahui", "papapa"));
        tes.add(new Patient("Emmanuelle", "Fouchere"));
        tes.add(new Patient("Nicolas", "Hulot"));
        tes.add(new Patient("Axel", "Galliot"));
        tes.add(new Patient("Nicolas", "Dubois"));
        tes.add(new Patient("Etienne ", "Wetzler"));
        return tes;
    }




}
