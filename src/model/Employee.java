package model;

public class Employee {
    private int idEmployee;
    private int idUser;


    public Employee(){}
    public Employee(int idEmployee, int idUser){
        this.idEmployee = idEmployee;
        this.idUser = idUser;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
    public int getIdEmployee() {
        return idEmployee;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", idUser=" + idUser +
                '}';
    }
}
