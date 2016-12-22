package com.example.medandco.medandco;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import patient.AdapterPatient;
import patient.Patient;

public class ListPatientActivity extends AppCompatActivity {

    ListView mListView;
    private AdapterPatient adapterPatient;
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
    private String ip;
    private String token;
    private ArrayList<Patient> patients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ip = (String) getIntent().getSerializableExtra("ip");
        token = (String) getIntent().getSerializableExtra("token");
        patients = (ArrayList<Patient>) getIntent().getSerializableExtra("array");
        //startService(new Intent(getBaseContext(), ServerRequest.class));

        mListView = (ListView) findViewById(R.id.list_patient);
        adapterPatient = new AdapterPatient(ListPatientActivity.this, patients);
        mListView.setAdapter(adapterPatient);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient)parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), DetailPatientActivity.class);
                intent.putExtra("idPatient", patient.getId());
                intent.putExtra("ip", ip);
                intent.putExtra("token",token);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rechargement des données", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("Notification Alert, Click Me!");
                mBuilder.setContentText("Hi, This is Android Notification Detail!");
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // notificationID allows you to update the notification later on.
                int notificationID = 12345;
                mNotificationManager.notify(notificationID, mBuilder.build());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class ServerPatientList extends AsyncTask<String, String, ArrayList<Patient>> {

        Context mContext;
        String ip;
        String token;
        public ServerPatientList(Context context){
            mContext = context;
        }

        @Override
        // index 0 = token, index 1 = ip
        protected ArrayList<Patient> doInBackground(String... params) {
            ArrayList<Patient> P = new ArrayList<Patient>();
            try  {

                String monUrl = "https://"+ params[1] +":8080/api/findAll";
                token = params[0];
                ip = params[1];
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

        @Override
        protected void onPostExecute(ArrayList<Patient> patients) {
            if (patients != null) {

                Intent i = new Intent(mContext, ListPatientActivity.class); //use context here
                i.putExtra("array", patients);
                i.putExtra("token",token);
                i.putExtra("ip",ip);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);


            }

        }

        private String convertStreamToString(InputStream is) {

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

}
