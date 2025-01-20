package service.terminal;

import model.Response;
import model.Store;
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
        for (Store store : stores){
            int i=0;
            System.out.println( (i+1) + " - " + store.getName());
        }

        int choice = InputService.intInput(1, stores.toArray().length, scanner);
    }
}
