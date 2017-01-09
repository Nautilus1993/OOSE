package com.team16.oose_project.sellingList;

/**
 * Created by lxx on 11/13/16.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.team16.oose_project.R;
import com.team16.oose_project.core.GuestHomePage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.io.SessionOutputBufferImpl;
import cz.msebera.android.httpclient.util.EntityUtils;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class NewItemActivity extends Activity{
    protected EditText nameTextField;
    protected EditText priceTextField;
    protected EditText descTextField;
    protected EditText avaiDateTextField;
    protected EditText expDateTextField;
    protected Spinner topCatSpinner;
    protected Spinner subCatSpinner;
    protected Spinner conditionSpinner;
    protected RadioGroup deliverRadioGroup;
    protected RadioButton radioButton;
    protected Calendar myCalendar;
    protected HashMap<String, Boolean> contacts;
    protected ImageButton emailButton;
    protected ImageButton snsButton;
    protected ImageButton textButton;
    protected ImageButton phoneButton;
    protected Button sendButton;
    private String url = "http://10.0.3.2:4567/sellList/post/";
    private String message = "New Item Posted!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_item);

        // Populate the category spinner
        Spinner spinner = (Spinner)findViewById(R.id.input_top_category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.top_category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Setup the sub-category spinner
        spinner = (Spinner)findViewById(R.id.input_sub_category);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.sub_bedroom_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Setup the condition spinner
        spinner = (Spinner)findViewById(R.id.input_condition);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.condition_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // get the field objects
        nameTextField = (EditText)findViewById(R.id.input_name);
        priceTextField = (EditText)findViewById(R.id.input_price);
        descTextField = (EditText)findViewById(R.id.input_description);
        avaiDateTextField = (EditText)findViewById(R.id.input_available_from);
        expDateTextField = (EditText)findViewById(R.id.input_available_to);
        topCatSpinner = (Spinner)findViewById(R.id.input_top_category);
        subCatSpinner = (Spinner)findViewById(R.id.input_sub_category);
        conditionSpinner = (Spinner)findViewById(R.id.input_condition);
        sendButton = (Button) findViewById(R.id.postSubmitButton);
        deliverRadioGroup = (RadioGroup) findViewById(R.id.input_deliver_group);
        myCalendar = Calendar.getInstance();
        emailButton = (ImageButton)findViewById(R.id.email_icon);
        snsButton = (ImageButton)findViewById(R.id.sns_icon);
        textButton = (ImageButton)findViewById(R.id.text_icon);
        phoneButton = (ImageButton)findViewById(R.id.phone_icon);

        //init value
        contacts = new HashMap<String, Boolean>(4);
        contacts.put("email", FALSE);
        contacts.put("sns", FALSE);
        contacts.put("text", FALSE);
        contacts.put("phone", FALSE);

        //add listeners
        setDatePickerListener(avaiDateTextField, "Select start date");
        setDatePickerListener(expDateTextField, "Select end date");
        addListenerOnSubmitButton();
        addListenerOnContactButtons(R.id.email_icon);
        addListenerOnContactButtons(R.id.sns_icon);
        addListenerOnContactButtons(R.id.text_icon);
        addListenerOnContactButtons(R.id.phone_icon);

    }

    protected void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.input_delivery_yes:
                if (checked)
                    break;
            case R.id.input_delivery_no:
                if (checked)
                    break;
        }
    }

    protected void addListenerOnContactButtons(final int id){
        final ImageButton button = (ImageButton)findViewById(id);
        button.setColorFilter(Color.argb(220, 255, 255, 255));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(button.isSelected()){
                    deleteContact(button);
                    button.setColorFilter(Color.argb(220, 255, 255, 255));//change back to dim
                    button.setSelected(FALSE);
                }else if(!button.isSelected()){
                    addContact(button);
                    button.setColorFilter(Color.argb(0, 0, 0, 0));//light up
                    button.setSelected(TRUE);
                }
            }
        });
    }

    protected String getRadioButtonText(){
        int selectedId = deliverRadioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        return radioButton.getText().toString();
    }

    protected void addListenerOnSubmitButton(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Check not null fields
                Boolean chk = checkFileds();
                // perform action on click
                if (TRUE == chk) {
                    postData();
                    //System.out.println("****button clicked! data posted!");
                }else{
                    Toast.makeText(getBaseContext(),"All fields are required",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void setDatePickerListener(final EditText field, final String title) {
        field.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar currentDate = Calendar.getInstance();
                int mYear = currentDate.get(Calendar.YEAR);
                int mMonth = currentDate.get(Calendar.MONTH);
                int mDay = currentDate.get(Calendar.DAY_OF_MONTH);

                //datepicker
                DatePickerDialog datePicker = new DatePickerDialog(
                        NewItemActivity.this, new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear,
                                          int selectedmonth, int selectedday) {
                        myCalendar.set(datepicker.getYear(), datepicker.getMonth(),
                                datepicker.getDayOfMonth());
                        updateLabel(field);
                    }
                }, mYear, mMonth, mDay);
                datePicker.setTitle(title);
                datePicker.show();
            }
        });
    }

    protected void updateLabel(EditText field) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        field.setText(sdf.format(myCalendar.getTime()));
    }

    protected void addContact(View contactButton){
        //System.out.println(contactButton.getContentDescription().toString() + "selected!!!!!!");
        String contact = contactButton.getContentDescription().toString();
        if(FALSE == contacts.get(contact)){
            contacts.put(contact, TRUE);
        }
    }

    protected void deleteContact(View contactButton){
        //System.out.println(contactButton.getContentDescription().toString() + "un-selected!!!!!!");
        String contact = contactButton.getContentDescription().toString();
        if(TRUE == contacts.get(contact)){
            contacts.put(contact, FALSE);
        }
    }

    protected JSONArray getContacts(){
        JSONArray jArr = new JSONArray();
        Set<Map.Entry<String, Boolean>> contactSet = contacts.entrySet();
        for(Map.Entry contact : contactSet){
            if(TRUE == contact.getValue()){
                jArr.put(contact.getKey().toString());
            }
        }
        //System.out.println("*****contacts are:" + jArr.toString());
        return jArr;
    }

    protected boolean checkFileds(){
        Boolean flag = TRUE;
        if(0 == nameTextField.length()){
            flag = FALSE;
            System.out.println("name not set");
        }else if(0 == priceTextField.length()){
            flag = FALSE;
            System.out.println("price not set");
        }else if(-1 == (deliverRadioGroup.getCheckedRadioButtonId())){
            flag = FALSE;
            System.out.println("deliver not set");
        }else if(0 == topCatSpinner.getSelectedItem().toString().length()){
            flag = FALSE;
            System.out.println("topcat not set");
        }else if(0 == subCatSpinner.getSelectedItem().toString().length()){
            flag = FALSE;
            System.out.println("subcat not set");
        }else if(0 == conditionSpinner.getSelectedItem().toString().length()){
            flag = FALSE;
            System.out.println("condition not set");
        }
        return flag;
    }

    protected void postData() {
        //get message from message box
        String sellerId = getSellerId(); //
        String category1 = topCatSpinner.getSelectedItem().toString();
        String category2 = subCatSpinner.getSelectedItem().toString();
        String name = nameTextField.getText().toString();
        String price = priceTextField.getText().toString();
        String imgLink = ""; //img
        if (imgLink == ""){ imgLink = "https://cdn.pixabay.com/photo/2014/03/29/09/17/" +
                "cat-300572_960_720.jpg";}
        String condition = conditionSpinner.getSelectedItem().toString();
        String isDeliver = getRadioButtonText();
        String pickUpAddress = ""; //not yet
        String description = descTextField.getText().toString();
        String postDate = getCurrDate();
        String avialableDate = avaiDateTextField.getText().toString();
        String expireDate = expDateTextField.getText().toString();
        JSONArray contacts = getContacts();

        //add data
        JSONObject json = new JSONObject();
        try {
            json.put("sellerId", sellerId);
            json.put("category1", category1);
            json.put("category2", category2);
            json.put("name", name);
            json.put("price", price);
            json.put("imgLink", imgLink);
            json.put("condition", condition);
            if(isDeliver.equals("Yes")){
                json.put("isDeliver", TRUE);
            }else{
                json.put("isDeliver", FALSE);
            }
            json.put("pickUpAddress", pickUpAddress);
            json.put("description", description);
            json.put("postDate", postDate);
            if(!avialableDate.isEmpty()) {
                json.put("avialableDate", avialableDate);
            }
            if(!avialableDate.isEmpty()) {
                json.put("expireDate", expireDate);
            }
            json.put("contactMethods", contacts);
        }catch(JSONException e){
            e.printStackTrace();
        }

        new PostTask().execute(json.toString());
    }

    protected String getUrl(){
        return url;
    }

    protected String getMessage(){
        return message;
    }


    protected String getSellerId(){
        return GuestHomePage.userId;
    }

    protected String getCurrDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date currDate = new Date();
        return sdf.format(currDate);
    }

    private class PostTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {

            try {
                // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getUrl() + getSellerId());
                httppost.setEntity(new StringEntity(data[0]));
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    String op = EntityUtils.toString((cz.msebera.android.httpclient.HttpEntity)
                            response.getEntity(), "UTF-8");//The response you get from your script
                    return op;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //reset the message text field
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), getMessage(), Toast.LENGTH_SHORT).show();
            jumpToHome();
        }

        public void jumpToHome(){
            Timer timer=new Timer();
            int deleyTime = 1500;
            final Intent i= new Intent(NewItemActivity.this, GuestHomePage.class);//homescreen of your app.
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            TimerTask task=new TimerTask(){
                @Override
                public void run(){
                    startActivity(i);
                }
            };
            timer.schedule(task, deleyTime);
        }
    }
}
