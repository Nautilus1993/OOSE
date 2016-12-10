package com.team16.project.ItemList;

import com.team16.project.Model.ItemListDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemListService {
    private ItemListDB itemListDB;
    private final Logger logger = LoggerFactory.getLogger(ItemListService.class);

    public ItemListService() {
        this.itemListDB = new ItemListDB();
    }

    public ArrayList<HashMap<String, Object>> getItemListInfo(String topCategory, String subCategory) throws itemListServiceException {
        return itemListDB.getItemListInfo(topCategory, subCategory);
    }

    public class itemListServiceException extends Exception{
        public itemListServiceException(String message, Throwable cause){
            super(message, cause);
        }
    }
}
