package com.team16.oose_project.wishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.team16.oose_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.team16.oose_project.core.GuestHomePage.userId;

/**
* This activity displays all wishes of a user in a list view.
* @author  Team 16
*/
public class WishList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        try {
            String jsonWishes = new WishTask().execute("http://10.0.3.2:4567/WishList", userId).get();
            String wishes = "{" + "\"wishes\":" + jsonWishes + "}";

            JSONObject object = new JSONObject(wishes);
            JSONArray jArray = object.getJSONArray("wishes");

            populateWishList(jArray);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Populate the list view with the input JSONArray
    private void populateWishList(JSONArray jsonArray) {
        // Construct the data source
        ArrayList<Wish> arrayOfWishes = Wish.fromJson(jsonArray);
        // Create the adapter to convert the array to views
        WishAdapter adapter = new WishAdapter(this, arrayOfWishes);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.wishes_list_view);
        listView.setAdapter(adapter);
    }
}
