package model;

public class Item {
    private int idItem;
    private String Name;
    private double price;

    public Item() {}
    public Item(int idItem, String Name, double price) {
        this.idItem = idItem;
        this.Name = Name;
        this.price = price;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", Name='" + Name + '\'' +
                ", price=" + price +
                '}';
    }
}
