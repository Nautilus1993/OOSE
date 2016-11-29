package com.team16.project.Item;

import com.team16.project.sqlite.ItemDetailDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ItemDetailService {
    private ItemDetailDB itemDetailDB;
    private final Logger logger = LoggerFactory.getLogger(ItemDetailService.class);

    public ItemDetailService() {
        this.itemDetailDB = new ItemDetailDB();
    }

    public HashMap<String, Object> getItemDetailInfo(String itemId) throws itemDetailServiceException {
        return itemDetailDB.getItemDetailInfo(itemId);
    }

    public class itemDetailServiceException extends Exception{
        public itemDetailServiceException(String message, Throwable cause){
            super(message, cause);
        }
    }
}
