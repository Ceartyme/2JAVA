package model;

public class Working {
    private int idStore;
    private int idEmployee;

    public Working() {}
    public Working(int idStore, int idEmployee) {
        this.idStore = idStore;
        this.idEmployee = idEmployee;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    @Override
    public String toString() {
        return "Working{" +
                "idEmployee=" + idEmployee +
                ", idStore=" + idStore +
                '}';
    }
}
