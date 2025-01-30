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
}