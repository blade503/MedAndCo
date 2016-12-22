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

    public static List<Patient> myListPatient = ServerRequest.getServerData();

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

    public static ArrayList<Patient> getServerData(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<Patient> P = new ArrayList<Patient>();
        try  {
            String monUrl = "https://10.238.48.164:8080/api/findAll";

            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(monUrl);

                urlConnection = (HttpsURLConnection) url.openConnection();
                HttpsURLConnection httpsConn = urlConnection;
                httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());

                String jsonReply;
                InputStream in = httpsConn.getInputStream();
                jsonReply = convertStreamToString(in);

                JSONObject obj = new JSONObject("{\"patients\":" + jsonReply + "}");
                JSONArray geodata = obj.getJSONArray("patients");
                int n = geodata.length();

                for (int i = 0; i < n; ++i) {
                    final JSONObject person = geodata.getJSONObject(i);
                    P.add(new Patient(person.getString("nom"), person.getString("prenom"), person.getString("_id")));
                }
                return P;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return P;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }




}
