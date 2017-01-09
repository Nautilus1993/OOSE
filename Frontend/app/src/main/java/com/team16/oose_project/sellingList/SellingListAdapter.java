package com.team16.oose_project.sellingList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team16.oose_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lxx on 11/21/16.
 */

public class SellingListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SellingItem> mDataSource;

    public SellingListAdapter(Context context, ArrayList<SellingItem> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.layout_selling_item, parent, false);

        // Get icon element
        ImageView iconImageView =
                (ImageView) rowView.findViewById(R.id.itemIcon);

        // Get itemPrice element
        TextView priceTextView =
                (TextView) rowView.findViewById(R.id.itemPrice);

        // Get itemName element
        TextView nameTextView =
                (TextView) rowView.findViewById(R.id.itemName);

        // Get itemCondition element
        TextView conditionTextView =
                (TextView) rowView.findViewById(R.id.itemCondition);

        // Get itemDelivery element
        TextView deliveryTextView =
                (TextView) rowView.findViewById(R.id.itemDelivery);

        // Get itemDate element
        TextView dateTextView =
                (TextView) rowView.findViewById(R.id.itemDate);

        // Get itemContacts element
        TextView contactsTextView =
                (TextView) rowView.findViewById(R.id.itemContacts);

        // Get itemEdit element
        Button editTextView =
                (Button) rowView.findViewById(R.id.itemEdit);

        // Get itemRemove element
        Button removeTextView =
                (Button) rowView.findViewById(R.id.itemRemove);

        // Get data from source object SellingItem
        SellingItem item = (SellingItem) getItem(position);

        //********Updating the row view’s views so they are displaying the sellingItem*******
        // Set price element
        priceTextView.setText(item.getPrice() == null? "$" : "$" + item.getPrice());

        // Set name element
        nameTextView.setText(item.getName() == null? "" : item.getName());

        // Set condition element
        conditionTextView.setText(item.getCondition() == null? "" : item.getCondition());

        // Set delivery element
        deliveryTextView.setText(item.isDeliver()? "Delivery" : "pick up");

        // Set availableDate element
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        Date ad = item.getAvialableDate();
        String adString = "";
        if (ad != null){
            adString = sdf.format(ad) + " ~ ";
        }
        Date ed = item.getExpireDate();
        String edString = "";
        if (ed != null){
            edString = sdf.format(ed);
        }
        dateTextView.setText(adString + edString);

        // Set contacts element
        String[] contacts = item.getContacts();
        String arr = "";
        if (contacts != null){
            for (String contact : contacts) {
                if(contact != null){
                    arr += contact.concat(" ");
                }
            }
        }
        contactsTextView.setText(arr);

        // Set edit element
        editTextView.setText("edit");

        // Set edit element
        removeTextView.setText("remove");

        // Set image element
        String imgLink = item.getIcon();
        Picasso.with(mContext).load(item.getIcon()).
                placeholder(R.drawable.girl).into(iconImageView);


        return rowView;
    }
}
