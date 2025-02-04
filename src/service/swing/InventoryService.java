package service.swing;

import model.Inventory;
import model.Item;
import model.Response;
import model.Store;
import repository.InventoryRepository;
import repository.ItemRepository;

import java.util.ArrayList;

public class InventoryService {
    protected static void updateAmount(int idItem,int idStore, int amount) {
        InventoryRepository.setAmountItemInStore(idStore,idItem,amount);
    }

    public static void removeItemFromStore(int idItem, int idStore) {
        InventoryRepository.deleteItemFromStore(idStore,idItem);
    }

    public static ArrayList<Item> getAllItemsNotInStore(Store store) {
        Response<ArrayList<Item>> itemsResponse = ItemRepository.getAllItems();
        Response<ArrayList<Inventory>> itemsInStoreResponse = InventoryRepository.getItemsByStore(store.getIdStore());
        ArrayList<Item> itemsInStore = new ArrayList<>();
        ArrayList<Item> itemsNotInStore = new ArrayList<>();
        if(itemsInStoreResponse.getValue()!=null) {
            for (Inventory inventory : itemsInStoreResponse.getValue()) {
                itemsInStore.add(ItemRepository.getItemById(inventory.getIdItem()).getValue());
            }
        }
        for(Item item : itemsResponse.getValue()){
            if(!itemsInStore.contains(item)){
                itemsNotInStore.add(item);
            }
        }
        return itemsNotInStore;
    }

    public static void addItemToStore(Item item, Store store) {
        InventoryRepository.createItemsInStore(store.getIdStore(), item.getIdItem(), 0);
    }
}
