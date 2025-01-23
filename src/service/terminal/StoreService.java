package service.terminal;

import model.*;
import repository.*;
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

    public static void createStoreController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Please enter the name of the store");

        String name = scanner.nextLine();

        Response<Store> storeResponse = StoreRepository.createStore(name);

        if (storeResponse.getMessage().equals("Success")){
            Store storeCreated = storeResponse.getValue();

            if (storeCreated != null) {
                System.out.println("Store created successfully");
                System.out.printf("ID: %d | Name: %s%n", storeCreated.getIdStore(), storeCreated.getName());
            }else {
                System.out.println("Error: Store was not created, but operation was marked successful.");
            }
        }else{
            System.out.println("Error creating store: " + storeResponse.getMessage());
        }
    }

    public static void addEmployeesController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Fetching all stores...");
        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();

        if(!storeResponse.getMessage().equals("Success")){
            System.out.println("Error : " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();

        if (stores.isEmpty()){
            System.out.println("No stores available. Please create a store first.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        System.out.println("Enter the ID of the store to which you want to add an employee:");
        int storeId = InputService.intInput(1, Integer.MAX_VALUE, scanner);

        boolean storeExists = stores.stream().anyMatch(store -> store.getIdStore() == storeId);
        if (!storeExists) {
            System.out.println("Invalid store ID. Operation cancelled.");
            return;
        }

        System.out.println("Fetching all users...");
        Response<ArrayList<User>> userResponse = UserRepository.getAllUsers();

        if (!userResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + userResponse.getMessage());
            return;
        }

        ArrayList<User> users = userResponse.getValue();
        if (users.isEmpty()) {
            System.out.println("No users available to hire. Please register users first.");
            return;
        }

        System.out.println("Available users:");
        for (User user : users) {
            System.out.printf("ID: %d | Username: %s | Role: %s%n",
                    user.getIdUser(),
                    user.getUsername(),
                    user.getRole());
        }

        System.out.println("Enter the ID of the user you want to hire:");
        int userId = InputService.intInput(1, Integer.MAX_VALUE, scanner);


        boolean userExists = users.stream().anyMatch(user -> user.getIdUser() == userId);
        if (!userExists) {
            System.out.println("Invalid user ID. Operation cancelled.");
            return;
        }


        String hireResult = WorkingRepository.hire(storeId, userId);
        if (hireResult.equals("User hired successfully")) {
            System.out.println("The user has been successfully hired and assigned to the store.");
        } else {
            System.out.println("Error hiring user: " + hireResult);
        }

        String updateRoleResult = WorkingRepository.updateUserRoleEmployee(userId, 2);
        if (updateRoleResult.equals("User role updated successfully")) {
            System.out.println("The user's role has been successfully updated to EMPLOYEE.");
        }else {
            System.out.println("Error updating user's role: " + updateRoleResult);
        }
    }

}
