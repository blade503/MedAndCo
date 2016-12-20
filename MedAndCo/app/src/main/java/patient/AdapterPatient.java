package patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.medandco.medandco.R;

import java.util.List;

/**
 * Created by alexandre on 20/12/2016.
 */

public class AdapterPatient extends ArrayAdapter<Patient>{

    //tweets est la liste des models à afficher
    public AdapterPatient(Context context, List<Patient> patients) {
        super(context, 0, patients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_patient,parent, false);
        }

        PatientViewHolder viewHolder = (PatientViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PatientViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.patient_name);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.patient_surname);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Patient patient = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(patient.getName());
        viewHolder.surname.setText(patient.getSurname());


        return convertView;
    }

    public class PatientViewHolder {

        public TextView name;
        public TextView surname;

    }
}
