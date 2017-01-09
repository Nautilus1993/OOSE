package com.team16.oose_project.Image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImageTask extends AsyncTask<Object, Object, Void> {
    private Bitmap image;
    private String name;

    public UploadImageTask(Bitmap image, String name){
        this.image = image;
        this.name = name;
    }

    @Override
    protected Void doInBackground(Object... params) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        System.out.println("Encoded String Image: " + encodeImage.length());
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try{
            URL url = new URL("http://10.0.3.2:4567/image/upload");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            // json object {name:"", image:""}
            JSONObject dataToSend = new JSONObject();
            dataToSend.put("name", name);
            dataToSend.put("image", encodeImage);
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream());

            writer.print(dataToSend.toString());
            writer.flush();
            writer.close();

            System.out.println(urlConnection.getResponseCode());
            return null;

        }catch(IOException e){
            Log.e("PlaceholderFragment", "Error", e);
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally{
            // close http connection
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }catch (final IOException e){
                    Log.e("PlaceholderFragment", "Error Closing stream", e);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
