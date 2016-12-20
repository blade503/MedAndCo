package com.example.medandco.medandco;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import patient.AdapterPatient;
import patient.Patient;

public class ListPatientActivity extends AppCompatActivity {

    ListView mListView;
    private AdapterPatient adapterPatient;
    List<Patient> myListPatient  = new ArrayList<Patient>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.list_patient);
        this.myListPatient = this.genererPatients();

        adapterPatient = new AdapterPatient(ListPatientActivity.this, this.myListPatient);
        mListView.setAdapter(adapterPatient);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "patient :" + patient, Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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


    private List<Patient> genererPatients(){
        List<Patient> tes = new ArrayList<Patient>();
        tes.add(new Patient("Aurélien", "Chapin"));
        tes.add(new Patient("Alexandre", "Wetzler"));
        tes.add(new Patient("Anthony", "Kavanagh"));
        tes.add(new Patient("Jiahui", "papapa"));
        tes.add(new Patient("Emmanuelle", "Fouchere"));
        tes.add(new Patient("Nicolas", "Hulot"));
        tes.add(new Patient("Axel", "Gallot"));
        tes.add(new Patient("Nicolas", "Dubois"));
        tes.add(new Patient("Etienne ", "Wetzler"));
        return tes;
    }
}
