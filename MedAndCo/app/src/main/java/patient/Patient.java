package patient;

import java.util.ArrayList;

/**
 * Created by alexandre on 20/12/2016.
 */

public class Patient {

    String name;
    String surname;
    String BirthDate;
    String age;
    String adresse;
    String CodePostal;
    String medecinTraitant;
    ArrayList<String> allergies;
    ArrayList<String> InfosComplemtaires;


    public Patient(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String toString(){
        return this.getName() +" "+ this.getSurname();
    }
}
