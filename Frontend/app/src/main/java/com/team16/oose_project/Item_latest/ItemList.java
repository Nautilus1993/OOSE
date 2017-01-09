package com.team16.oose_project.Item_latest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.MyAccount.MyAccountDisplay;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ItemList extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlist);

        ListView list = (ListView) findViewById(R.id.itemlist);
        TextView itemTypeName = (TextView) findViewById(R.id.itemListName);
        Intent intent = getIntent();
        String itemsDetail = intent.getStringExtra("allItems");
        String itemType = intent.getStringExtra("itemType");

        itemTypeName.setText(itemType);

        try {
            JSONObject object = new JSONObject(itemsDetail);
            JSONArray jArray = object.getJSONArray("returnValues");
            final ArrayList<Item> arrayOfItems = Item.fromJson(jArray);
            ItemAdapter adapter = new ItemAdapter(this, arrayOfItems);
            list.setAdapter(adapter);

            //click
            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                //need switch to item detail page later
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                {
                    Toast.makeText(ItemList.this, "" + position, Toast.LENGTH_SHORT).show();
                    //once you have position, you can use it to find certain item in arrayOfItems, and then use intent to transfer data from
                    //itemlist page to itemdeatil page.

                    Item selectedItem = new Item();
                    selectedItem = arrayOfItems.get(position);

                    Log.d("Selected Item Id", String.valueOf(selectedItem.getItemId()));

                    Intent itemDetail = new Intent(ItemList.this, ItemDetail.class);
                    itemDetail.putExtra("itemId", String.valueOf(selectedItem.getItemId()));
                    startActivity(itemDetail);

                    Log.d("ItemName",selectedItem.getName());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}