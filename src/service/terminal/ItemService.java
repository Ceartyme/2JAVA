package service.terminal;

import model.Item;
import model.Response;
import repository.ItemRepository;
import service.InputService;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemService {
    public static void createItemController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Enter the name of the new item: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Item name cannot be empty. Operation cancelled.");
            return;
        }

        System.out.println("Enter the price of the new item:");
        double itemPrice = InputService.doubleInput(0.00, scanner);
        Response<Item> createItemResponse = ItemRepository.createItem(name, itemPrice);
        if (createItemResponse.getMessage().equals("Success")) {
            Item createdItem = createItemResponse.getValue();
            System.out.printf("Item created successfully! ID: %d, Name: %s, Price: %.2f%n",
                    createdItem.getIdItem(),
                    createdItem.getName(),
                    createdItem.getPrice());
        } else {
            System.out.println("Error creating item: " + createItemResponse.getMessage());
        }
    }

    public  static void deleteItemController(Scanner scanner){
        scanner.nextLine();
        System.out.println("Fetching all items...");
        Response<ArrayList<Item>> allItems = ItemRepository.getAllItems();
        if(!allItems.getMessage().equals("Success")){
            System.out.println("Error fetching all items: " + allItems.getMessage());
        }

        ArrayList<Item> items = allItems.getValue();
        if(items.isEmpty()){
            System.out.println("Items list is empty. Operation cancelled.");
        }

        System.out.println("List of available items:");
        for (Item item : items) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f%n", item.getIdItem(), item.getName(), item.getPrice());
        }
        Response<ArrayList<Integer>> itemIdResponse = ItemRepository.getIdItems();
        int idItem = InputService.idInput(scanner, itemIdResponse);
        boolean itemExists = items.stream().anyMatch(item -> item.getIdItem() == idItem);
        if (!itemExists) {
            System.out.println("Invalid item ID. Deletion cancelled.");
            return;
        }

        String result = ItemRepository.deleteItem(idItem);
        if (result.equals("Item deleted Successfully")) {
            System.out.println("Item has been successfully deleted.");
        } else {
            System.out.println("Error deleting item: " + result);
        }
    }

    public static void updateItemController(Scanner scanner){
        System.out.println("Fetching all items...");

        Response<ArrayList<Item>> allItems = ItemRepository.getAllItems();
        if(!allItems.getMessage().equals("Success")){
            System.out.println("Error fetching all items: " + allItems.getMessage());
        }

        ArrayList<Item> items = allItems.getValue();

        if(items.isEmpty()){
            System.out.println("No items available in the system.");
            return;
        }

        System.out.println("Available items:");
        for (Item item : items) {
            System.out.println("ID: " + item.getIdItem() + " | Name: " + item.getName() + " | Price: $" + item.getPrice());
        }

        Response<ArrayList<Integer>> itemIdResponse = ItemRepository.getIdItems();
        int itemId = InputService.idInput(scanner, itemIdResponse);

        if(itemId == 0){
            System.out.println("Operation cancelled.");
            return;
        }


        Response<Item> selectedItemResponse = ItemRepository.getItemById(itemId);

        if (!selectedItemResponse.getMessage().equals("Success")) {
            System.out.println("Error: " + selectedItemResponse.getMessage());
            return;
        }

        Item selectedItem = selectedItemResponse.getValue();

        scanner.nextLine();
        System.out.println("Enter the new name for the item (or press Enter to keep current name: " + selectedItem.getName() + "):");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            newName = selectedItem.getName();
        }

        System.out.println("Enter the new price for the item (or 0 to keep current price: $" + selectedItem.getPrice() + "):");
        double newPrice = InputService.doubleInput(0.01, scanner);

        if (newPrice == 0) {
            newPrice = selectedItem.getPrice();
        }

        selectedItem.setName(newName);
        selectedItem.setPrice(newPrice);

        String result = ItemRepository.updateItem(selectedItem);

        if (result.equals("Item Updated Successfully")) {
            System.out.println("✅ Item updated successfully!");
        } else {
            System.out.println("❌ Error: " + result);
        }

    }
}