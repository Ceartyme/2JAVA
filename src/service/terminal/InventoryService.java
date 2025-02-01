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

public class InventoryService {
    public static void createItemsToStoreController(Scanner scanner){
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
        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
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
            Response<ArrayList<Integer>> itemIdResponse = ItemRepository.getIdItems();
            int itemId = InputService.idInput(scanner, itemIdResponse);
            if (itemId == 0) {
                System.out.println("Operation cancelled.");
                break;
            }

            boolean itemExists = items.stream().anyMatch(item -> item.getIdItem() == itemId);
            if (!itemExists) {
                System.out.println("Invalid item ID. Try again.");
                continue;
            }

            String addItemResult = InventoryRepository.createItemsInStore(storeId, itemId, 0);
            if (addItemResult.equals("Item successfully added or updated in the store")) {
                System.out.printf("Item ID %d added to store ID %d with quantity %d.%n", itemId, storeId, 0);
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
        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
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
        ArrayList<Integer> ListIds = new ArrayList<>();
        for (Inventory inventory : inventories) {
            Response<Item> itemResponse = ItemRepository.getItemById(inventory.getIdItem());
            if (itemResponse.getMessage().equals("Success")) {
                Item item = itemResponse.getValue();
                ListIds.add(inventory.getIdItem());
                System.out.printf("Item ID: %d | Name: %s | Price: %.2f | Amount: %d%n", item.getIdItem(), item.getName(), item.getPrice(), inventory.getAmount());
            } else {
                System.out.printf("Item ID: %d | Error: %s%n", inventory.getIdItem(), itemResponse.getMessage());
            }
        }

        Response<ArrayList<Integer>> itemIdResponse = new Response<>(ListIds);
        int itemId = InputService.idInput(scanner, itemIdResponse);

        if (itemId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        String deleteResult = InventoryRepository.deleteItemFromStore(storeId, itemId);
        if (deleteResult.equals("Item deleted successfully")) {
            System.out.println("The item has been successfully deleted from the store.");
        } else {
            System.out.println("Error: " + deleteResult);
        }

    }

    public static void increaseItemController(int loggedUserid, Scanner scanner){
        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> storeResponse = StoreRepository.getStoresByEmployeeId(loggedUserid);
        if (!storeResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("You are not assigned to any store.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.println("ID: " + store.getIdStore() + " | Store Name: " + store.getName());
        }
        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStoresByEmployeeId(loggedUserid);
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        Response<ArrayList<Inventory>> stockResponse = InventoryRepository.getItemsByStore(storeId);
        if (!stockResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + stockResponse.getMessage());
            return;
        }

        ArrayList<Inventory> stockList = stockResponse.getValue();
        if (stockList.isEmpty()) {
            System.out.println("No items found in this store.");
            return;
        }

        System.out.println("Items available in the store:");
        ArrayList<Integer> ListIds = new ArrayList<>();
        for (Inventory inventory : stockList) {
            Response<Item> itemResponse = ItemRepository.getItemById(inventory.getIdItem());
            if (!itemResponse.getMessage().equals("Success")) {
                System.out.println("Error: " + itemResponse.getMessage());
                continue;
            }
            Item item = itemResponse.getValue();
            ListIds.add(inventory.getIdItem());
            System.out.println("Item ID: " + item.getIdItem() +
                    " | Name: " + item.getName() +
                    " | Price: $" + item.getPrice() +
                    " | Amount: " + inventory.getAmount());
        }
        Response<ArrayList<Integer>> itemIdResponse = new Response<>(ListIds);
        int itemId = InputService.idInput(scanner, itemIdResponse);

        if (itemId == 0){
            System.out.println("Operation cancelled.");
            return;
        }


        System.out.println("Enter the quantity to increase (positive integer):");
        int increaseAmount = InputService.intInput(1, Integer.MAX_VALUE, scanner);
        String result = InventoryRepository.increaseItemInStore(storeId, itemId, increaseAmount);
        if (!result.equals("Item quantity successfully increased.")) {
            System.out.println("Error: " + result);
        } else {
            System.out.println("Item quantity increased successfully!");
        }
    }

    public static void decreaseItemController(int loggedUserid, Scanner scanner){
        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> storeResponse = StoreRepository.getStoresByEmployeeId(loggedUserid);
        if (!storeResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("You are not assigned to any store.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.println("ID: " + store.getIdStore() + " | Store Name: " + store.getName());
        }
        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStoresByEmployeeId(loggedUserid);
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        Response<ArrayList<Inventory>> stockResponse = InventoryRepository.getItemsByStore(storeId);
        if (!stockResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + stockResponse.getMessage());
            return;
        }

        ArrayList<Inventory> stockList = stockResponse.getValue();
        if (stockList.isEmpty()) {
            System.out.println("No items found in this store.");
            return;
        }

        System.out.println("Items available in the store:");
        ArrayList<Integer> ListIds = new ArrayList<>();

        for (Inventory inventory : stockList) {
            Response<Item> itemResponse = ItemRepository.getItemById(inventory.getIdItem());
            if (!itemResponse.getMessage().equals("Success")) {
                System.out.println("Error: " + itemResponse.getMessage());
                continue;
            }
            Item item = itemResponse.getValue();
            ListIds.add(inventory.getIdItem());
            System.out.println("Item ID: " + item.getIdItem() +
                    " | Name: " + item.getName() +
                    " | Price: $" + item.getPrice() +
                    " | Amount: " + inventory.getAmount());
        }
        Response<ArrayList<Integer>> itemIdResponse = new Response<>(ListIds);
        int itemId = InputService.idInput(scanner, itemIdResponse);

        if (itemId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        Inventory selectedInventory = stockList.stream()
                .filter(inventory -> inventory.getIdItem() == itemId)
                .findFirst()
                .orElse(null);


        int currentAmount = selectedInventory.getAmount();

        System.out.println("Enter the quantity to decrease (positive integer):");
        int decreaseAmount = InputService.intInput(1, Integer.MAX_VALUE, scanner);

        if ((currentAmount-decreaseAmount) < 0){
            System.out.println("The item has no stock left to decrease.");
            return;
        }

        String result = InventoryRepository.decreaseItemInStore(storeId, itemId, decreaseAmount);
        if (!result.equals("Item quantity successfully decreased.")) {
            System.out.println("Error: " + result);
        } else {
            System.out.println("Item quantity decreased successfully!");
        }
    }
}