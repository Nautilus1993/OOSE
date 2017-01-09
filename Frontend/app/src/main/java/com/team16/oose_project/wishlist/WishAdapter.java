package com.team16.oose_project.wishlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.team16.oose_project.Item_latest.ItemDetail;
import com.team16.oose_project.Item_latest.ItemList;
import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;

import java.util.ArrayList;

/**
* This class loads all the wishes of user from the database.
* @author  Team 16
*/
public class WishAdapter extends ArrayAdapter<Wish> {
    public WishAdapter(Context context, ArrayList<Wish> wishes) {
        super(context, 0, wishes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Wish wish = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_wishes, parent, false);
        }

        Button wishDetailButton = (Button) convertView.findViewById(R.id.wish_detail);

        wishDetailButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent itemDetail = new Intent(getContext(), ItemDetail.class);
                itemDetail.putExtra("itemId", wish.getItemId());
                getContext().startActivity(itemDetail);
            }

        });

        Button wishRemoveButton = (Button) convertView.findViewById(R.id.wish_remove);

        wishRemoveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new RemoveTask().execute("http://10.0.3.2:4567/WishList/remove", GuestHomePage.userId, wish.getItemId());
                remove(wish);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Item removed from wish list",
                        Toast.LENGTH_SHORT).show();
            }

        });



        // Lookup view for data population
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.wish_list_thumbnail);
        TextView tvName = (TextView) convertView.findViewById(R.id.wish_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.wish_price);
        TextView tvCondition = (TextView) convertView.findViewById(R.id.wish_condition);
        TextView tvAvailableDate = (TextView) convertView.findViewById(R.id.wish_available_date);
        TextView tvExpireDate = (TextView) convertView.findViewById(R.id.wish_expire_date);
        // Populate the data into the template view using the data object
        Picasso.with(getContext()).load(wish.getUrl()).into(tvImage);
        tvName.setText("Title: " + wish.getName());
        tvPrice.setText("Price: $" + wish.getPrice());
        tvCondition.setText("Condition: " + wish.getCondition());
        tvAvailableDate.setText("Available on: " + wish.getAvailableDate());
        tvExpireDate.setText("Expire on: " + wish.getExpirationDate());
        // Return the completed view to render on screen
        return convertView;
    }
}
