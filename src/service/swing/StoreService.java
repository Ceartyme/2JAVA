package service.swing;

import model.Response;
import model.Store;
import model.User;
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
}
