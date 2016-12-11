package com.team16.project.core;


import com.team16.project.Image.UserPhotoController;
import com.team16.project.Item.ItemDetailController;
import com.team16.project.ItemList.ItemListController;
import com.team16.project.Login.LoginController;
import com.team16.project.Model.MyAccountDB;
import com.team16.project.MyAccount.MyAccountController;
import com.team16.project.MyAccount.MyAccountService;
import com.team16.project.MyList.SellingList.SellingListController;
import com.team16.project.MyList.SellingList.SellingListService;
import com.team16.project.MyList.WishList.WishController;
import com.team16.project.registration.email.EmailRegistrationController;
import com.team16.project.registration.password.PasswordController;
import com.team16.project.registration.phone.PhoneRegistrationController;
import com.team16.project.subscribe.SubscribeController;
import com.team16.project.subscribe.SubscribeSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class Bootstrap {
    public static final String DATABASE = "jdbc:sqlite:ProjectDB.db";
    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws SQLException {
        new EmailRegistrationController();
        new PhoneRegistrationController();
        new PasswordController();
        new LoginController();
        new ItemDetailController();
        new UserPhotoController();
        new ItemListController();
        new WishController();
        new UserPhotoController();
        new SubscribeController();

        MyAccountDB projectDB = new MyAccountDB();
        // Create model and controller.
        MyAccountService model = new MyAccountService(projectDB);
        new MyAccountController(model);
        
        // SubscribeSchedule subscribeSchedule = new SubscribeSchedule();

        try {
            SellingListService ser = new SellingListService();
            new SellingListController(ser);
        } catch (SellingListService.SellingListServiceException ex) {
            LOGGER.error("Failed to create a SellingListController instance. Aborting");
        }
    }
}
