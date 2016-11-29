package com.team16.project.core;


import com.team16.project.Image.UploadUserPhotoController;
import com.team16.project.Item.ItemDetailController;
import com.team16.project.Item.ItemDetailService;
import com.team16.project.Login.LoginController;
import com.team16.project.MyAccount.MyAccountController;
import com.team16.project.MyAccount.MyAccountService;
import com.team16.project.registration.email.EmailRegistrationController;
import com.team16.project.registration.password.PasswordController;
import com.team16.project.registration.phone.PhoneRegistrationController;
import com.team16.project.sqlite.ItemDetailDB;
import com.team16.project.sqlite.MyAccountDB;


import java.sql.SQLException;

public class Bootstrap {
    public static final String DATABASE = "jdbc:sqlite:ProjectDB.db";

    public static void main(String[] args) throws SQLException {
        new EmailRegistrationController();
        new PhoneRegistrationController();
        new PasswordController();
        new LoginController();
        new ItemDetailController();

        new UploadUserPhotoController();

        MyAccountDB projectDB = new MyAccountDB();
        // Create model and controller.
        MyAccountService model = new MyAccountService(projectDB);
        new MyAccountController(model);
    }
}
