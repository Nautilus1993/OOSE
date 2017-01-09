package com.team16.oose_project.Subcategory;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.MyAccount.MyAccountDisplay;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.sellingList.NewItemActivity;
import com.team16.oose_project.sellingList.SellingListDisplayActivity;
import com.team16.oose_project.wishlist.WishList;

public class LivingRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_me) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent myProfile = new Intent(LivingRoom.this, MyAccountDisplay.class);
                    startActivity(myProfile);
                    System.out.println("***********Me tab selected");
                }

                if(tabId == R.id.tab_selling){
                    Intent sellingList = new Intent(LivingRoom.this,
                            SellingListDisplayActivity.class);
                    startActivity(sellingList);
                    System.out.println("*********** Selling tab selected");
                }

                if(tabId == R.id.tab_post){
                    Intent post = new Intent(LivingRoom.this,
                            NewItemActivity.class);
                    startActivity(post);
                    System.out.println("*********** Post tab selected");
                }

                if(tabId == R.id.tab_home){
                    Intent guestHomePage = new Intent(LivingRoom.this, GuestHomePage.class);
                    startActivity(guestHomePage);
                    System.out.println("*********** Home tab selected");
                }

                if(tabId == R.id.tab_wishes){
                    Intent wishList = new Intent(LivingRoom.this, WishList.class);
                    startActivity(wishList);
                }

            }
        });
    }
}
