package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return idItem == item.idItem; // Comparaison basée sur l'ID unique
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem);
    }
}
