package com.team16.oose_project.Subcategory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.Item_latest.ItemList;
import com.team16.oose_project.Item_latest.ItemListDisplayTask;
import com.team16.oose_project.MyAccount.MyAccountDisplay;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.sellingList.NewItemActivity;
import com.team16.oose_project.sellingList.SellingListDisplayActivity;
import com.team16.oose_project.wishlist.WishList;

import java.util.concurrent.ExecutionException;

public class Bedroom extends AppCompatActivity {

    private ItemListDisplayTask itemListDisplayTask = null;
    private  String baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedroom);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_me) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent myProfile = new Intent(Bedroom.this, MyAccountDisplay.class);
                    startActivity(myProfile);
                    System.out.println("***********Me tab selected");
                }

                if(tabId == R.id.tab_selling){
                    Intent sellingList = new Intent(Bedroom.this,
                            SellingListDisplayActivity.class);
                    startActivity(sellingList);
                    System.out.println("*********** Selling tab selected");
                }

                if(tabId == R.id.tab_post){
                    Intent post = new Intent(Bedroom.this,
                            NewItemActivity.class);
                    startActivity(post);
                    System.out.println("*********** Post tab selected");
                }

                if(tabId == R.id.tab_home){
                    Intent guestHomePage = new Intent(Bedroom.this, GuestHomePage.class);
                    startActivity(guestHomePage);
                    System.out.println("*********** Home tab selected");
                }

                if(tabId == R.id.tab_wishes){
                    Intent wishList = new Intent(Bedroom.this, WishList.class);
                    startActivity(wishList);
                }

            }
        });
    }

    public void fromSubBedroomToBeds (View view) throws ExecutionException, InterruptedException{
//        Intent beds = new Intent(this, imageTest.class);
//        startActivity(beds);

        //URL needs change
        baseURL = "http://10.0.3.2:4567/itemlist/Bedroom/Beds";

        itemListDisplayTask = new ItemListDisplayTask("Beds");
        String itemListDisplayResult = itemListDisplayTask.execute(baseURL).get();

        Log.d("Result", itemListDisplayResult);

        if (itemListDisplayResult != null && !itemListDisplayResult.isEmpty()) {
            //use Intent to transfer data
            Intent itemlistShow = new Intent(this, ItemList.class);
            itemlistShow.putExtra("allItems", itemListDisplayResult);
            itemlistShow.putExtra("itemType", "Beds");
            startActivity(itemlistShow);
        } else {
            //switch to subscribe view
        }
    }
}
