package com.team16.oose_project.core;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.Image.Image;
import com.team16.oose_project.MyAccount.MyAccountDisplay;
import com.team16.oose_project.R;
import com.team16.oose_project.Subcategory.Balcony;
import com.team16.oose_project.Subcategory.Bathroom;
import com.team16.oose_project.Subcategory.Bedroom;
import com.team16.oose_project.Subcategory.Children;
import com.team16.oose_project.Subcategory.Kitchen;
import com.team16.oose_project.Subcategory.LivingRoom;
import com.team16.oose_project.Subcategory.StudyRoom;
import com.team16.oose_project.login.Login;
import com.team16.oose_project.registration.email.EmailRegistration;
import com.team16.oose_project.sellingList.NewItemActivity;
import com.team16.oose_project.sellingList.SellingListDisplayActivity;
import com.team16.oose_project.wishlist.WishList;

import java.util.concurrent.ExecutionException;

public class GuestHomePage extends AppCompatActivity implements  View.OnClickListener {

    public static String userId = "0";
    Button btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesthomepage);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(myToolbar);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);

        btnImage = (Button) findViewById(R.id.moreButton);
        btnImage.setOnClickListener(this);



        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if(tabId == R.id.tab_me) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    if (GuestHomePage.userId.equals("0")) {
                        Intent intent = new Intent(GuestHomePage.this, Login.class);
                        startActivity(intent);
                    } else {
                        Intent myProfile = new Intent(GuestHomePage.this, MyAccountDisplay.class);
                        startActivity(myProfile);
                        System.out.println("***********Me tab selected");
                    }
                }

                if(tabId == R.id.tab_selling){
                    if (GuestHomePage.userId.equals("0")) {
                        Intent intent = new Intent(GuestHomePage.this, Login.class);
                        startActivity(intent);
                    } else {
                        Intent sellingList = new Intent(GuestHomePage.this,
                                SellingListDisplayActivity.class);
                        startActivity(sellingList);
                        System.out.println("*********** Selling tab selected");
                    }
                }

                if(tabId == R.id.tab_post){
                    if (GuestHomePage.userId.equals("0")) {
                        Intent intent = new Intent(GuestHomePage.this, Login.class);
                        startActivity(intent);
                    } else {
                        Intent post = new Intent(GuestHomePage.this,
                                NewItemActivity.class);
                        startActivity(post);
                        System.out.println("*********** Post tab selected");
                    }
                }

                if(tabId == R.id.tab_home){
                    Intent guestHomePage = new Intent(GuestHomePage.this, GuestHomePage.class);
                    //startActivity(guestHomePage);
                    System.out.println("*********** Home tab selected");
                }

                if(tabId == R.id.tab_wishes){
                    if (GuestHomePage.userId.equals("0")) {
                        Intent intent = new Intent(GuestHomePage.this, Login.class);
                        startActivity(intent);
                    } else {
                        Intent wishList = new Intent(GuestHomePage.this, WishList.class);
                        startActivity(wishList);
                    }
                }

            }
        });

//        meButton = (Button) findViewById(R.id.tab_me);
//
//        meButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent myProfile = new Intent(view.getContext(), MyAccountDisplay.class);
//                startActivity(myProfile);
//            }
//        });
    }

    public void fromHomepageToSubBedroom (View view) throws ExecutionException, InterruptedException{
        Intent bedroom = new Intent(this, Bedroom.class);
        startActivity(bedroom);
    }
    
     public void fromHomepageToSubStudyRoom (View view) throws ExecutionException, InterruptedException{
        Intent studyroom = new Intent(this, StudyRoom.class);
        startActivity(studyroom);
    }

    public void fromHomepageToSubKitchen (View view) throws ExecutionException, InterruptedException{
        Intent kitchen = new Intent(this, Kitchen.class);
        startActivity(kitchen);
    }

    public void fromHomepageToSubBathroom(View view) throws ExecutionException, InterruptedException{
        Intent bathroom = new Intent(this, Bathroom.class);
        startActivity(bathroom);
    }

    public void fromHomepageToSubLivingRoom(View view) throws ExecutionException, InterruptedException{
        Intent livingroom = new Intent(this, LivingRoom.class);
        startActivity(livingroom);
    }

    public void fromHomepageToSubBalcony(View view) throws ExecutionException, InterruptedException{
        Intent balcony = new Intent(this, Balcony.class);
        startActivity(balcony);
    }

    public void fromHomepageToSubChildren(View view) throws ExecutionException, InterruptedException{
        Intent children = new Intent(this, Children.class);
        startActivity(children);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signup:
                Intent emailRegistration = new Intent(this, EmailRegistration.class);
                startActivity(emailRegistration);
                return true;

            case R.id.action_login:
                if (userId.equals("0")) {
                    Intent login = new Intent(this, Login.class);
                    startActivity(login);
                }
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.moreButton:
                Intent imageExample = new Intent(GuestHomePage.this, Image.class);
                startActivity(imageExample);
        }
    }
}
