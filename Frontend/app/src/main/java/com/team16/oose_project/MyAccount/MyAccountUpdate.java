package com.team16.oose_project.MyAccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.team16.oose_project.R;
import com.team16.oose_project.Subcategory.Balcony;
import com.team16.oose_project.core.GuestHomePage;
import com.team16.oose_project.sellingList.NewItemActivity;
import com.team16.oose_project.sellingList.SellingListDisplayActivity;
import com.team16.oose_project.wishlist.WishList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static com.team16.oose_project.core.GuestHomePage.userId;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.team16.oose_project.core.GuestHomePage.userId;

public class MyAccountUpdate extends AppCompatActivity implements View.OnClickListener {
    /**
     * Hangbao: Used for select store the result when user selects image from phone
     */
    public static final int RESULT_LOAD_IMAGE = 1;

    ImageView a_image;
    EditText a_username;
    EditText a_email;
    EditText a_phone;
    EditText a_address;
    EditText a_city;
    EditText a_state;
    EditText a_zipcode;
    Button updatePost;

    UserProfile userProfile;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_update);

        // Fetch views
        a_image = (ImageView) findViewById(R.id.userimage);
        a_username = (EditText) findViewById(R.id.username);
        a_email = (EditText) findViewById(R.id.email);
        a_phone = (EditText) findViewById(R.id.phone);
        a_address = (EditText) findViewById(R.id.address);
        a_city = (EditText) findViewById(R.id.city);
        a_state = (EditText) findViewById(R.id.state);
        a_zipcode = (EditText) findViewById(R.id.zipcode);
        updatePost = (Button) findViewById(R.id.updateButton);

        //init bottomBar, TODO refactor to tool class
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_me) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    Intent myProfile = new Intent(MyAccountUpdate.this, MyAccountDisplay.class);
                    startActivity(myProfile);
                    System.out.println("***********Me tab selected");
                }
                if(tabId == R.id.tab_selling){
                    Intent sellingList = new Intent(MyAccountUpdate.this,
                            SellingListDisplayActivity.class);
                    startActivity(sellingList);
                    System.out.println("*********** Selling tab selected");
                }
                if(tabId == R.id.tab_post){
                    Intent post = new Intent(MyAccountUpdate.this,
                            NewItemActivity.class);
                    startActivity(post);
                    System.out.println("*********** Post tab selected");
                }
                if(tabId == R.id.tab_home){
                    Intent guestHomePage = new Intent(MyAccountUpdate.this, GuestHomePage.class);
                    startActivity(guestHomePage);
                    System.out.println("*********** Home tab selected");
                }
                if(tabId == R.id.tab_wishes){
                    Intent wishList = new Intent(MyAccountUpdate.this, WishList.class);
                    startActivity(wishList);
                }
            }
        });

        // add click listener to Button "POST"
        updatePost.setOnClickListener(this);
        a_image.setOnClickListener(this);
    }

    public static String POST(String url, UserProfile userProfile){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("username", userProfile.getUsername());
            jsonObject.accumulate("email", userProfile.getEmail());
            jsonObject.accumulate("phoneNum", userProfile.getPhone());
            jsonObject.accumulate("address", userProfile.getAddress());
            jsonObject.accumulate("city", userProfile.getCity());
            jsonObject.accumulate("state", userProfile.getState());
            jsonObject.accumulate("zipCode", userProfile.getZipCode());
            jsonObject.accumulate("image", userProfile.getImage());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.userimage){
            System.out.println("click to choose photo");
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            System.out.println("before choose photo activity");
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            System.out.println("After choose photo activity");
        }

        if(view.getId() == R.id.updateButton){

            if(!validate())
                Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
            // call AsynTask to perform network operation on separate thread

            userProfile = new UserProfile();
            userProfile.setUsername(a_username.getText().toString());
            userProfile.setEmail(a_email.getText().toString());
            userProfile.setPhone(a_phone.getText().toString());
            userProfile.setAddress(a_address.getText().toString());
            userProfile.setCity(a_city.getText().toString());
            userProfile.setState(a_state.getText().toString());
            userProfile.setZipcode(a_zipcode.getText().toString());

            /**
             * App will do the following 2 things when user update photo:
             * 1) Encode image into string and put it into "POST" request.
             * 2) Generate local cache photo file. (saved as [userId].png)
             */
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (a_image.getDrawable() != null) {
                Bitmap bitImage = ((BitmapDrawable) a_image.getDrawable()).getBitmap();
                // Base64 encode to String image
                bitImage.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
                String strImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                System.out.println("Encoded String User Photo length: " + strImage.length());
                userProfile.setImage(strImage);

            } else {
                // user does not upload photo, leave image field as String
                System.out.println("user does not upload photo");
                userProfile.setImage("");
            }
            String url = "http://10.0.3.2:4567/MyAccount/AccountModify/" + userId;
            new HttpAsyncTask(userProfile).execute(url);
            Intent intent = new Intent(this, MyAccountDisplay.class);
            startActivity(intent);
        }
    }

    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            Uri selectedImage = data.getData();
            a_image.setImageURI(selectedImage);
            System.out.println("User select photo from sdcard.");
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private UserProfile userProfile;

        public HttpAsyncTask(UserProfile userProfile){
            this.userProfile = userProfile;
        }

        @Override
        protected String doInBackground(String... urls) {
            System.out.println("hello4!");
            System.out.println(urls[0]);

            return POST(urls[0], userProfile);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate(){
        if(a_username.getText().toString().trim().equals(""))
            return false;
        else if(a_email.getText().toString().trim().equals(""))
            return false;
        else if(a_phone.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }


    // Helper method to convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}