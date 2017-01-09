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
import java.net.URL;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.extras.Base64;


public class DownloadImageTask extends AsyncTask<Object, Object, Bitmap> {

    /**
     * To send user photo back to Image activity
     */

    private String imageName;
    Bitmap bmImage;

    public DownloadImageTask(String imageName){
        this.imageName = imageName;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String forecastJsonStr = "";

        /**
         * Get photo file update time
         */
        String path = ".cache/image/1.png";
        File f = new File(path);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String modifiedTime = format.format(f.lastModified());
        System.out.println("File Last modified at: " + modifiedTime);


        try{
            // construct url, open connection
            URL url = new URL("http://10.0.3.2:4567/image/download/user.png/1");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.connect();

            // Read input stream into a string
            StringBuffer buffer = new StringBuffer();

            // Http status code (>400 for error, supposed 200 ok)
            int status = conn.getResponseCode();
            if(status >= HttpStatus.SC_BAD_REQUEST){
                System.out.println("Error Code: " + status);
            }else{
                System.out.println("Status Code: " + status);
            }

            // Get response from server
            InputStream response = conn.getInputStream();
            if(response == null){
                // "GET" request get nothing from server
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(response));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0){
                // stream is empty
                return null;
            }
            forecastJsonStr = buffer.toString();
            System.out.println("receive response from server : " + forecastJsonStr );

            // decode image string into a Bitmap object
            byte[] data = Base64.decode(forecastJsonStr, Base64.DEFAULT);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;
            bmImage = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
        }catch (IOException e){
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

    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
    }
}
