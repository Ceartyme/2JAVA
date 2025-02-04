package service.swing;

import model.*;
import repository.*;

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

    public static ArrayList<User> getUserFromStore(Store store) throws Exception {
        ArrayList<User> returnValue = new ArrayList<>();
        Response<ArrayList<User>> responseAdmin= UserRepository.getAllAdmin();
        Response<ArrayList<User>> responseEmployee = WorkingRepository.getEmployeesFromStore(store.getIdStore());
        if(!Objects.equals(responseAdmin.getMessage(), "Error not any administrator in database") && !Objects.equals(responseAdmin.getMessage(), "Success")){
            throw new Exception(responseAdmin.getMessage());
        }
        if(!Objects.equals(responseEmployee.getMessage(), "There are no employees in this store") && !Objects.equals(responseEmployee.getMessage(), "Success")){
            throw new Exception(responseEmployee.getMessage());
        }
        if(!Objects.equals(responseAdmin.getMessage(), "Error not any administrator in database")){
            returnValue.addAll(responseAdmin.getValue());
        }
        if(!Objects.equals(responseEmployee.getMessage(), "There are no employees in this store")){
            returnValue.addAll(responseEmployee.getValue());
        }

        return returnValue;
    }

    public static void fire(User user, Store store) {
        WorkingRepository.fire(store.getIdStore(), user.getIdUser());
    }

    public static void hire(User user, Store store) {
        WorkingRepository.hire(store.getIdStore(), user.getIdUser());
    }

    public static ArrayList<User> getAllEmployeesNotInStore(Store store) {
        Response<ArrayList<User>> employeesResponse = UserRepository.getAllEmployees();
        ArrayList<User> employees;
        if(!Objects.equals(employeesResponse.getMessage(), "Error not any employee in database")){
            employees = employeesResponse.getValue();
        }else {
            employees = new ArrayList<>();
        }
        Response<ArrayList<User>> employeesInStoreResponse = WorkingRepository.getEmployeesFromStore(store.getIdStore());
        ArrayList<User> employeesInStore;
        if(!Objects.equals(employeesInStoreResponse.getMessage(), "There are no employees in this store")){
            employeesInStore = employeesInStoreResponse.getValue();
        }else {
            employeesInStore = new ArrayList<>();
        }
        ArrayList<User> employeesNotInStore = new ArrayList<>();
        for(User employee : employees){
            if(!employeesInStore.contains(employee)){
                employeesNotInStore.add(employee);
            }
        }
        return employeesNotInStore;
    }
}
