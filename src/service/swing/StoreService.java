package service.swing;

import model.*;
import repository.InventoryRepository;
import repository.ItemRepository;
import repository.StoreRepository;
import repository.WorkingRepository;

import java.util.ArrayList;
import java.util.Objects;

public class StoreService {
    protected static ArrayList<Store> getAllStores(){
        Response<ArrayList<Store>> storeResponse = StoreRepository.getAllStores();
        if(Objects.equals(storeResponse.getMessage(), "There are no Stores in the database")){
            return null;
        }
        return storeResponse.getValue();
    }

    protected static void addStoreToEmployee(User employee, String storeName){
        Response<Store> store = StoreRepository.getStoreByName(storeName);
        WorkingRepository.hire(store.getValue().getIdStore(), employee.getIdUser());
    }

    protected static Store getStoreFromName(String name){
        Response<Store> storeResponse = StoreRepository.getStoreByName(name);
        return storeResponse.getValue();
    }

    protected static boolean isWorking(User employee, Store store){
        return WorkingRepository.isWorking(employee.getIdUser(),store.getIdStore()).getValue();
    }

    public static void createStore(String name) throws Exception{
        if(name.isEmpty()){
            throw new Exception("Every field must be filled");
        }
        if(Objects.equals(StoreRepository.getStoreByName(name).getMessage(), "Success")){
            throw new Exception("An store with that name already exists");
        }
        Response<Store> response = StoreRepository.createStore(name);
        GeneralService.checkResponse(response);
    }

    public static ArrayList<Inventory> getItemsFromStore(Store store) throws Exception{
        Response<ArrayList<Inventory>> response = InventoryRepository.getItemsByStore(store.getIdStore());
        if(Objects.equals(response.getMessage(), "This store does not exist or does not have any item")){
            return new ArrayList<>();
        }
        GeneralService.checkResponse(response);
        return response.getValue();
    }
}
