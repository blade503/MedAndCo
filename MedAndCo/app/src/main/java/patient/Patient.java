package patient;

/**
 * Created by alexandre on 20/12/2016.
 */

public class Patient {

    String name;
    String surname;
    String id;

    public Patient(String name, String surname, String id){
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getId() { return id; }

    public void setId(String id) {this.id = id; }

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
