package model;

public class Store {
    private int idStore;
    private String name;

    public Store() {}
    public Store(int idStore, String name) {
        this.idStore = idStore;
        this.name = name;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Store{" +
                "idStore=" + idStore +
                ", name='" + name + '\'' +
                '}';
    }
}
