package service.terminal;

import model.Inventory;
import model.Item;
import model.Response;
import model.Store;
import repository.ItemRepository;
import repository.InventoryRepository;
import repository.StoreRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class InventoryService {
    public static void addOrUpdateItemsToStoreController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> StoresResponse = StoreRepository.getAllStores();

        if (!StoresResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + StoresResponse.getMessage());
            return;
        }

        ArrayList<Store> Stores = StoresResponse.getValue();

        if (Stores.isEmpty()) {
            System.out.println("No stores available. Please create a store first.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : Stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        System.out.println("Enter the ID of the store to which you want to add items:");
        int storeId = InputService.intInput(1, Integer.MAX_VALUE, scanner);

        boolean storeExists = Stores.stream().anyMatch(store -> store.getIdStore() == storeId);
        if (!storeExists) {
            System.out.println("Invalid store ID. Operation cancelled.");
            return;
        }

        System.out.println("Fetching all items...");
        Response<ArrayList<Item>> itemsResponse = ItemRepository.getAllItems();

        if (!itemsResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + itemsResponse.getMessage());
        }

        ArrayList<Item> items = itemsResponse.getValue();

        if (items.isEmpty()) {
            System.out.println("No items available. Please create items first.");
            return;
        }

        System.out.println("Available items:");
        for (Item item : items) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f%n",
                    item.getIdItem(),
                    item.getName(),
                    item.getPrice());
        }

        System.out.println("Enter the ID of the item to which you want to add items (or 0 to finish):");
        while (true) {
            int itemId = InputService.intInput(0, Integer.MAX_VALUE, scanner);
            if (itemId == 0) {
                System.out.println("Finished adding items to store.");
                break;
            }

            boolean itemExists = items.stream().anyMatch(item -> item.getIdItem() == itemId);
            if (!itemExists) {
                System.out.println("Invalid item ID. Try again.");
                continue;
            }

            System.out.println("Enter the quantity of the item:");
            int quantity = InputService.intInput(1, Integer.MAX_VALUE, scanner);

            String addItemResult = InventoryRepository.addOrUpdateItemsInStore(storeId, itemId, quantity);

            if (addItemResult.equals("Item successfully added or updated in the store")) {
                System.out.printf("Item ID %d added to store ID %d with quantity %d.%n", itemId, storeId, quantity);
            } else {
                System.out.println("Error adding item: " + addItemResult);
            }
        }
    }

    public static void deleteItemsFromStoreController(Scanner scanner){
        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> StoresResponse = StoreRepository.getAllStores();
        if (!StoresResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + StoresResponse.getMessage());
            return;
        }

        ArrayList<Store> Stores = StoresResponse.getValue();

        if (Stores.isEmpty()) {
            System.out.println("No stores available. Please create a store first.");
        }

        System.out.println("Available stores:");
        for (Store store : Stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        System.out.println("Enter the ID of the store to which you want to delete items:");
        int storeId = InputService.intInput(1, Integer.MAX_VALUE, scanner);

        boolean storeExists = Stores.stream().anyMatch(store -> store.getIdStore() == storeId);
        if (!storeExists) {
            System.out.println("Invalid store ID. Operation cancelled.");
        }

        System.out.println("Fetching all items...");
        Response<ArrayList<Inventory>> inventoryResponse = InventoryRepository.getItemsByStore(storeId);

        if (!inventoryResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + inventoryResponse.getMessage());
            return;
        }

        ArrayList<Inventory> inventories = inventoryResponse.getValue();
        if (inventories.isEmpty()) {
            System.out.println("No items found in this store.");
            return;
        }

        System.out.println("Items in the store:");
        for (Inventory inventory : inventories) {
            Response<Item> itemResponse = ItemRepository.getItemById(inventory.getIdItem());
            if (itemResponse.getMessage().equals("Success")) {
                Item item = itemResponse.getValue();
                System.out.printf("Item ID: %d | Name: %s | Price: %.2f | Amount: %d%n",
                        item.getIdItem(), item.getName(), item.getPrice(), inventory.getAmount());
            } else {
                System.out.printf("Item ID: %d | Error: %s%n", inventory.getIdItem(), itemResponse.getMessage());
            }
        }

        System.out.println("Enter the ID of the item you want to delete:");
        int itemId = InputService.intInput(1, Integer.MAX_VALUE, scanner);

        boolean itemExists = inventories.stream().anyMatch(inventory -> inventory.getIdItem() == itemId);
        if (!itemExists) {
            System.out.println("Invalid item ID. Operation cancelled.");
            return;
        }

        String deleteResult = InventoryRepository.deleteItemFromStore(storeId, itemId);
        if (deleteResult.equals("Item deleted successfully")) {
            System.out.println("The item has been successfully deleted from the store.");
        } else {
            System.out.println("Error: " + deleteResult);
        }

    }
}
