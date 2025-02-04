package model;

public class Inventory {
    private int idStores;
    private int Amount;
    private int idItem;

    public Inventory(int idStores, int Amount, int idItem) {
        this.idStores = idStores;
        this.Amount = Amount;
        this.idItem = idItem;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
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
