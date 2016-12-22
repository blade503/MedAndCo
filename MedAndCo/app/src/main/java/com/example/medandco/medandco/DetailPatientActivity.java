package com.example.medandco.medandco;

import android.net.SSLCertificateSocketFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DetailPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView firstname = (TextView) findViewById(R.id.tvFirstname);
        TextView name = (TextView) findViewById(R.id.tvName);
        TextView dateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
        TextView address = (TextView) findViewById(R.id.tvAddress);
        TextView zipCode = (TextView) findViewById(R.id.tvZipCode);
        TextView city = (TextView) findViewById(R.id.tvCity);
        TextView ssn = (TextView) findViewById(R.id.tvSSN);
        TextView phone = (TextView) findViewById(R.id.tvPhone);
        TextView medecin = (TextView) findViewById(R.id.MedecinTraitant);


        String idPatient = (String) getIntent().getSerializableExtra("idPatient");

        String monUrl = "https://10.238.48.164:8080/api/findPatient?data=" + idPatient;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(monUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
            httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
            httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());

            InputStream in = httpsConn.getInputStream();
            String jsonReply = convertStreamToString(in);

            System.out.println(jsonReply);

            JSONObject obj = new JSONObject("{\"patient\":" + jsonReply + "}");
            JSONArray patient = obj.getJSONArray("patient");

            JSONObject person = patient.getJSONObject(0);

            JSONObject objU = new JSONObject("{\"contactU\":[" + person.getString("contactUrgence") + "]}");
            JSONArray contactU = objU.getJSONArray("contactU");

            JSONObject contact = contactU.getJSONObject(0);

            name.setText(person.getString("nom"));
            firstname.setText(person.getString("prenom"));
            dateOfBirth.setText(person.getString("dateNaissance"));
            address.setText(person.getString("addresse"));
            zipCode.setText(person.getString("codePostal"));
            city.setText(person.getString("ville"));
            phone.setText(person.getString("telephone"));
            ssn.setText(person.getString("numSecu"));
            medecin.setText(person.getString("medecinTraitant"));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        toolbar.setSubtitle("Informations patient");

    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
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
