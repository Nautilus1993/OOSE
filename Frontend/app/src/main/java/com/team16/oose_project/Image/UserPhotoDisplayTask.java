package com.team16.oose_project.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.extras.Base64;

public class UserPhotoDisplayTask extends AsyncTask<Object, Object, Bitmap>{

    Bitmap bmImage;

    /**
     * This activity should start when click "Me" tab in navigation bar
     *
     * Here we implement cache strategy
     * 1) First time send server "photo file last modified time"
     * 2) Whether send the real request to server depend on the server resposne
     * if server response is null, then time is same with server.
     * if server response is not null, that's because modified time is not same
     * with server and server end photo string back to app.
     */

    @Override
    protected Bitmap doInBackground(Object... params) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String imageStr = "";

        /**
         * Get photo file update time
         */
        String path = ".cache/image/1.png";
        File f = new File(path);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy-HH-mm");
        String modifiedTime = format.format(f.lastModified());
        System.out.println("File Last modified at: " + modifiedTime);

        try{
            // construct url, open conncection

            String link = "http://10.0.3.2:4567/image/download/1/"+ modifiedTime;
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(1000);
            conn.connect();

            // Read input stream into a string
            StringBuffer buffer = new StringBuffer();

            // Check http response status code
            int status = conn.getResponseCode();
            if(status >= HttpStatus.SC_BAD_REQUEST){
                System.out.println("Error Code: " + status);
            }else{
                System.out.println("Status Code: " + status);
            }

            // Get response from server
            InputStream response = conn.getInputStream();

            if(response == null) {
                // "GET" request get nothing from server
                // photo file modified time is same for APP and server
                System.out.println("Server said: use your local photo file");
                return null;
            }else {
                reader = new BufferedReader(new InputStreamReader(response));
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0){
                    // stream is empty
                    return null;
                }
                imageStr = buffer.toString();

                // decode image String into Bitmap object
                byte[] data = Base64.decode(imageStr, Base64.DEFAULT);
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inMutable = true;
                bmImage = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }catch (final IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bmImage;
    }
}
