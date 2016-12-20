package patient;

/**
 * Created by alexandre on 20/12/2016.
 */

public class Patient {

    String name;
    String surname;

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
