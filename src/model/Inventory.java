package model;

public class Inventory {
    private final int idStores;
    private final int Amount;
    private final int idItem;

    public Inventory(int idStores, int Amount, int idItem) {
        this.idStores = idStores;
        this.Amount = Amount;
        this.idItem = idItem;
    }

    public int getAmount() {
        return Amount;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getIdStores() {
        return idStores;
    }


    @Override
    public String toString() {
        return "Inventory{" +
                "Amount=" + Amount +
                ", idStores=" + idStores +
                ", idItem=" + idItem +
                '}';
    }

}
