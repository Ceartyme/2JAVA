package service.swing;

import model.Inventory;
import model.Item;
import model.Response;
import model.Store;
import repository.ItemRepository;
import repository.StoreRepository;

import java.util.ArrayList;
import java.util.Objects;

public class ItemService {
    protected static ArrayList<Item> getAllItems() throws Exception{
        Response<ArrayList<Item>> allItems = ItemRepository.getAllItems();
        if(Objects.equals(allItems.getMessage(), "There are no Items in the database")){
            return new ArrayList<>();
        }
        GeneralService.checkResponse(allItems);
        return allItems.getValue();
    }

    protected static void updateItem(String name, double price, Item item) throws Exception{
        boolean nameChanged = false;
        boolean priceChanged = false;
        if(!Objects.equals(name,item.getName())){
            item.setName(name);
            nameChanged=true;
        }
        if(price!= item.getPrice()){
            item.setPrice(price);
            priceChanged=true;
        }
        if(!nameChanged && !priceChanged){
            throw new Exception("Nothing has been changed");
        }
        if(Objects.equals(ItemRepository.getItemByName(name).getMessage(), "Success")&&nameChanged){
            throw new Exception("An item with this name already exists");
        }
        String response = ItemRepository.updateItem(item);
        if(!Objects.equals(response,"Item Updated Successfully")){
            throw new Exception(response);
        }
    }

    protected static void deleteItem(Item item) throws Exception{
        String response = ItemRepository.deleteItem(item.getIdItem());
        if(!Objects.equals(response,"Item deleted Successfully")){
            throw new Exception(response);
        }
    }

    protected static ArrayList<Store> getStoresFromItem(Item item) throws Exception{
        ArrayList<Store> stores = new ArrayList<>();
        Response<ArrayList<Inventory>> response = ItemRepository.getStores(item.getIdItem());
        GeneralService.checkResponse(response);
        for(Inventory inventory: response.getValue()){
            Response<Store> responseStore = StoreRepository.getStoreById(inventory.getIdStores());
            GeneralService.checkResponse(responseStore);
            stores.add(responseStore.getValue());
        }
        return stores;
    }

    protected static void createItem(String name, double price) throws Exception{
        if(name.isEmpty()){
            throw new Exception("Every field must be filled");
        }
        if(Objects.equals(ItemRepository.getItemByName(name).getMessage(), "Success")){
            throw new Exception("An item with this name already exists");
        }
        Response<Item> response = ItemRepository.createItem(name,price);
        GeneralService.checkResponse(response);
    }
}
