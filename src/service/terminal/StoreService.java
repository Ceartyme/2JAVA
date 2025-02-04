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
        for (Store store : stores){
            System.out.println( store.getIdStore() + " - " + store.getName());
        }

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();

        int choice = InputService.idInput(scanner, storeIdResponse);

        if (choice == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        Response<Store> selectedStoreResponse = StoreRepository.getStoreById(choice);
        if(!selectedStoreResponse.getMessage().equals("Success")){
            System.out.println("Error : " + selectedStoreResponse.getMessage());
            return;
        }

        Store selectedStore = selectedStoreResponse.getValue();

        System.out.println("Selected store : " + selectedStore.getName());
        Response<ArrayList<Inventory>> inventoryResponse = InventoryRepository.getItemsByStore(selectedStore.getIdStore());
        if(!inventoryResponse.getMessage().equals("Success")){
            System.out.println("Error : This store does not have any items");
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

        System.out.println("Please enter the name of the store");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Store name cannot be empty. Please enter a valid name.");
             createStoreController(scanner);
             return;
        }

        Response<ArrayList<Store>> storeNameResponse = StoreRepository.getAllStores();
        if (!storeNameResponse.getMessage().equals("Success")) {
            System.out.println("Error fetching stores: " + storeNameResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeNameResponse.getValue();
        boolean storeExists = stores.stream().anyMatch(store -> store.getName().equalsIgnoreCase(name));
        if (storeExists) {
            System.out.println("Error: A store with this name already exists. Please choose a different name.");
            createStoreController(scanner);
            return;
        }


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

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        System.out.println("Fetching all users...");
        Response<ArrayList<User>> userResponse = UserRepository.getAllEmployeesAndUsers();
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

        Response<ArrayList<Integer>> userIdResponse = UserRepository.getIdUser();
        int userId = InputService.idInput(scanner, userIdResponse);

        if (userId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        String hireResult = WorkingRepository.hire(storeId, userId);


        if (hireResult.equals("User hired successfully")) {
            System.out.println("The user has been successfully hired and assigned to the store.");

            Response<User> userHiredResponse = UserRepository.getUserById(userId);
            if (!userHiredResponse.getMessage().equals("Success")) {
                System.out.println("Error receiving user.");
            }

            User userHired = userHiredResponse.getValue();
            if(userHired.getRole() == Role.USER) {
                String updateRoleResult = WorkingRepository.updateUserRoleEmployee(userId, 2);
                if (updateRoleResult.equals("User role updated successfully")) {
                    System.out.println("The user's role has been successfully updated to EMPLOYEE.");
                }else {
                    System.out.println("Error updating user's role: " + updateRoleResult);
                }
            }

        } else {
            System.out.println("Error hiring user: User already hire in this store");
        }
    }


    public static void addUpdatedUserEmployeesController(Scanner scanner, int userId){

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

        boolean running = true;

        while(running){
            Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
            int storeId = InputService.idInput(scanner, storeIdResponse);

            if (storeId == 0){
                System.out.println("Operation cancelled.");
                running=false;
                return;
            }



            String hireResult = WorkingRepository.hire(storeId, userId);
            if (hireResult.equals("User hired successfully")) {
                System.out.println("The user has been successfully hired and assigned to the store.");

            } else {
                System.out.println("Error hiring user: User already hire in this store");
            }
        }

    }





    public static void removeEmployeesController(Scanner scanner){
        scanner.nextLine();

        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();
        if(!storeResponse.getMessage().equals("Success")){
            System.out.println("Error : " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("No stores available.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);


        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        System.out.println("Fetching employees assigned to the store...");
        Response<ArrayList<User>> employeeResponse = WorkingRepository.getEmployeesFromStore(storeId);
        if (!employeeResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + employeeResponse.getMessage());
            return;
        }

        ArrayList<User> employees = employeeResponse.getValue();
        if (employees.isEmpty()) {
            System.out.println("No employees found in this store.");
            return;
        }

        System.out.println("Employees in store:");
        ArrayList<Integer> employeesIds = new ArrayList<>();
        for (User user : employees) {
            employeesIds.add(user.getIdUser());
            System.out.printf("ID: %d | Username: %s | Role: %s%n",
                    user.getIdUser(),
                    user.getUsername(),
                    user.getRole());
        }

        Response<ArrayList<Integer>> employeeIdResponse = new Response<>(employeesIds);
        int userId = InputService.idInput(scanner, employeeIdResponse);

        if (userId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        String fireResult = WorkingRepository.fire(storeId, userId);
        if (fireResult.equals("Employee fired Successfully")) {
            System.out.println("The employee has been successfully removed from the store.");

            Response<ArrayList<Store>> userStoresResponse = StoreRepository.getStoresByEmployeeId(userId);

            Response<User> userHiredResponse = UserRepository.getUserById(userId);
            if (!userHiredResponse.getMessage().equals("Success")) {
                System.out.println("Error receiving user.");
            }

            User userFired = userHiredResponse.getValue();

            if (userStoresResponse.getMessage().equals("No stores found for the given employee ID.") && userFired.getRole() == Role.EMPLOYEE) {
                String updateRoleResult = WorkingRepository.updateUserRoleEmployee(userId, 3);
                if (updateRoleResult.equals("User role updated successfully")) {
                    System.out.println("The user's role has been updated to USER.");

                } else {
                    System.out.println("Error updating user's role: " + updateRoleResult);

                }
            }
        } else {
            System.out.println("Error removing employee: " + fireResult);
        }
    }

    public static void displayWorkersController(Scanner scanner) {
        System.out.println("Fetching all stores...");
        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();
        if (!storeResponse.getMessage().equals("Success")) {
            System.out.println("Error : " + storeResponse.getMessage());
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("No stores available. Please create a store first.");
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }


        System.out.println("\nFetching all administrators...");
        Response<ArrayList<User>> adminResponse = UserRepository.getAllAdmin();
        if (!adminResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + adminResponse.getMessage());
        } else {
            ArrayList<User> admins = adminResponse.getValue();
            if (admins.isEmpty()) {
                System.out.println("No administrators found.");
            } else {
                System.out.println("\nList of administrators:");
                for (User admin : admins) {
                    System.out.printf("ID: %d | Username: %s | Email: %s%n",
                            admin.getIdUser(), admin.getUsername(), admin.getEmail());
                }
            }
        }

        System.out.println("\nFetching employees for the selected store...");
        Response<ArrayList<User>> employeesResponse = WorkingRepository.getEmployeesFromStore(storeId);
        if (!employeesResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + employeesResponse.getMessage());
        } else {
            ArrayList<User> employees = employeesResponse.getValue();
            if (employees.isEmpty()) {
                System.out.println("No employees found for this store.");
            } else {
                System.out.println("\nList of employees in the store:");
                for (User employee : employees) {
                    System.out.printf("ID: %d | Username: %s | Email: %s%n",
                            employee.getIdUser(), employee.getUsername(), employee.getEmail());
                }
            }
        }
    }

    public static void displayWorkersEmployeesController(int loggedUserid, Scanner scanner) {
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

        System.out.println("\nFetching all administrators...");
        Response<ArrayList<User>> adminResponse = UserRepository.getAllAdmin();
        if (!adminResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + adminResponse.getMessage());
        } else {
            ArrayList<User> admins = adminResponse.getValue();
            if (admins.isEmpty()) {
                System.out.println("No administrators found.");
            } else {
                System.out.println("\nList of administrators:");
                for (User admin : admins) {
                    System.out.printf("ID: %d | Username: %s | Email: %s%n",
                            admin.getIdUser(), admin.getUsername(), admin.getEmail());
                }
            }
        }

        System.out.println("\nFetching employees for the selected store...");
        Response<ArrayList<User>> employeesResponse = WorkingRepository.getEmployeesFromStore(storeId);
        if (!employeesResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + employeesResponse.getMessage());
        } else {
            ArrayList<User> employees = employeesResponse.getValue();
            if (employees.isEmpty()) {
                System.out.println("No employees found for this store.");
            } else {
                System.out.println("\nList of employees in the store:");
                for (User employee : employees) {
                    System.out.printf("ID: %d | Username: %s | Email: %s%n",
                            employee.getIdUser(), employee.getUsername(), employee.getEmail());
                }
            }
        }
    }

    public static void deleteStoreController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Fetching all stores...");
        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();
        if(!storeResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + storeResponse.getMessage());
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("No stores available. Please create a store first.");
        }

        System.out.println("List of available stores:");
        for (Store store : stores) {
            System.out.printf("ID: %d | Name: %s%n", store.getIdStore(), store.getName());
        }

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        System.out.println("Fetching employees assigned to the store...");
        Response<ArrayList<User>> employeeResponse = WorkingRepository.getEmployeesFromStore(storeId);
        if (!employeeResponse.getMessage().equals("Success")) {
            System.out.println(employeeResponse.getMessage());
            return;
        }

        ArrayList<User> employees = employeeResponse.getValue();

        String result = StoreRepository.deleteStore(storeId);
        if(result.equals("Store deleted Successfully")) {
            System.out.println("The store has been successfully deleted.");

            for (User employee : employees) {
                int employeeId = employee.getIdUser();

                Response<ArrayList<Store>> userStoresResponse = StoreRepository.getStoresByEmployeeId(employeeId);

                if (userStoresResponse.getMessage().equals("No stores found for the given employee ID.") && employee.getRole() == Role.EMPLOYEE) {
                    String updateRoleResult = WorkingRepository.updateUserRoleEmployee(employeeId, 3);
                    if (updateRoleResult.equals("User role updated successfully")) {
                        System.out.printf("Employee ID: %d has been demoted to USER.%n", employeeId);
                    } else {
                        System.out.println("Error updating user's role: " + updateRoleResult);
                    }
                }
            }



        }else{
            System.out.println("Error: " + result);
        }




    }



    public static void updateStoreController(Scanner scanner){
        System.out.println("Fetching all stores...");

        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();

        if (!storeResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + storeResponse.getMessage());
            return;
        }

        ArrayList<Store> stores = storeResponse.getValue();
        if (stores.isEmpty()) {
            System.out.println("No stores available in the system.");
            return;
        }

        System.out.println("Available stores:");
        for (Store store : stores) {
            System.out.println("ID: " + store.getIdStore() + " | Store Name: " + store.getName());
        }

        Response<ArrayList<Integer>> storeIdResponse = StoreRepository.getIdStores();
        int storeId = InputService.idInput(scanner, storeIdResponse);

        if (storeId == 0){
            System.out.println("Operation cancelled.");
            return;
        }

        Response<Store> selectedStoreResponse = StoreRepository.getStoreById(storeId);

        if (!selectedStoreResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + selectedStoreResponse.getMessage());
            return;
        }

        Store selectedStore = selectedStoreResponse.getValue();

        scanner.nextLine();

        String newName;
        while (true) {
            System.out.println("Enter the new name for the store (or press Enter to keep current name: " + selectedStore.getName() + "):");
            newName = scanner.nextLine().trim();

            if (newName.isEmpty()) {
                newName = selectedStore.getName();
                break;
            }


            Response<Store> existingStoreResponse = StoreRepository.getStoreByName(newName);
            if (existingStoreResponse.getMessage().equals("Success") && existingStoreResponse.getValue() != null) {
                System.out.println("A store with this name already exists. Please enter another name.");
            } else {
                break;
            }
        }

        selectedStore.setName(newName);
        String result = StoreRepository.updateStore(selectedStore);


        if (result.equals("Store Updated Successfully")) {
            System.out.println("Store updated successfully!");
        } else {
            System.out.println("Error: " + result);
        }




    }
}