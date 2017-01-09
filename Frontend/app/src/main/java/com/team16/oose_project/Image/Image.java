package com.team16.oose_project.Image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.team16.oose_project.R;

import java.util.concurrent.ExecutionException;

public class Image extends AppCompatActivity implements View.OnClickListener{

    /**
     * Used for select store the result when user selects image from phone
     */
    private static final int RESULT_LOAD_IMAGE = 1;

    ImageView imageTobeUpload, imageDownloaded;
    Button btnUploadImage, btnDownloadImage;
    EditText uploadImageName, downloadImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imageTobeUpload = (ImageView) findViewById(R.id.uploadImage);
        imageDownloaded = (ImageView) findViewById(R.id.downloadImage);

        btnUploadImage = (Button) findViewById(R.id.btnUpload);
        btnDownloadImage = (Button) findViewById(R.id.btnDownload);

        uploadImageName = (EditText) findViewById(R.id.uploadName);
        downloadImageName = (EditText) findViewById(R.id.downloadName);

        imageTobeUpload.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
        btnDownloadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.uploadImage:
                Intent galleryIntent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // intent, requestCode
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.btnUpload:
                Bitmap imageToUpload = ((BitmapDrawable) imageTobeUpload.getDrawable()).getBitmap();
                String uploadName = uploadImageName.getText().toString();
                UploadImageTask uploadTask = new UploadImageTask(imageToUpload, uploadName);
                uploadTask.execute();
                break;

            case R.id.btnDownload:
                String downloadName = downloadImageName.getText().toString();
                DownloadImageTask downloadTask = new DownloadImageTask(downloadName);
                try {
                    Bitmap bm = downloadTask.execute().get();
                    Drawable d = new BitmapDrawable(getResources(), bm);
                    imageDownloaded.setImageDrawable(d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageTobeUpload.setImageURI(selectedImage);
            System.out.println("select photo from gallery");
        }
    }
}