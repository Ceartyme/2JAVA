package service.terminal;

import model.Inventory;
import model.Item;
import model.Response;
import model.Store;
import repository.InventoryRepository;
import repository.ItemRepository;
import repository.StoreRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class StoreService {
    public static void BrowseInventory(Scanner scanner){
        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();

        if (!storeResponse.getMessage().equals("Success")){
            System.out.println("Error : " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();

        if (stores.isEmpty()){
            System.out.println("Nothing to show");
            return;
        }

        System.out.println("All stores available :");
        int i=0;
        for (Store store : stores){
            i++;
            System.out.println( i + " - " + store.getName());
        }

        int choice = InputService.intInput(1, stores.toArray().length, scanner);

        Store selectedStore = stores.get(choice - 1);


        Response<ArrayList<Inventory>> inventoryResponse = InventoryRepository.getItemsByStore(selectedStore.getIdStore());

        if(!inventoryResponse.getMessage().equals("Success")){
            System.out.println("Error : " + inventoryResponse.getMessage());
            return;
        }

        ArrayList<Inventory> inventories = inventoryResponse.getValue();

        if (inventories.isEmpty()){
            System.out.println("Nothing to show");
            return;
        }

        System.out.println("Inventory for store: " + selectedStore.getName());
        for (Inventory inventory : inventories) {
            Response<Item> itemResponse = ItemRepository.getItemById(inventory.getIdItem());

            if (itemResponse.getMessage().equals("Success")) {
                Item item = itemResponse.getValue();
                System.out.println("Item: " + item.getName() + ", Price: " + item.getPrice() + ", Quantity: " + inventory.getAmount());
            } else {
                System.out.println("Error retrieving item: " + itemResponse.getMessage());
            }
        }

    }
}
