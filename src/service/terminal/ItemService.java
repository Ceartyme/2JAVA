package service.terminal;

import java.util.Scanner;

import model.Item;
import model.Response;
import repository.ItemRepository;
import service.InputService;

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
}
