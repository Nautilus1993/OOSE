package com.team16.oose_project.Item_latest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team16.oose_project.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.itemImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.itemName);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.itemPrice);
        TextView tvCondition = (TextView) convertView.findViewById(R.id.itemCondition);
        TextView tvDelivery = (TextView) convertView.findViewById(R.id.itemDelivery);
//        Button button = (Button) convertView.findViewById(R.id.itemStar);

//        button.setFocusable(false);

        // Populate the data into the template view using the data object

        if (item.getImgLink() != null && item.getImgLink().length() != 0 ) {
            Picasso.with(getContext()).load(item.getImgLink()).into(tvImage);
        } else {
            Picasso.with(getContext()).load("http://www.swimport.com/plates/whiteblank.jpg").into(tvImage);
        }

        tvName.setText(item.getName());
        tvPrice.setText("$ " + String.valueOf(item.getPrice()));
        tvCondition.setText(item.getCondition());

        Log.d("isDeliver", item.isDeliver());

        if (item.isDeliver().equals("1")) {
            tvDelivery.setText("Delivery");
        } else {
            tvDelivery.setText("Pick Up");
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
