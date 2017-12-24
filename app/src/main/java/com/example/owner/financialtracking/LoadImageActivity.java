package com.example.owner.financialtracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class LoadImageActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_UPLOAD = 2;
    TextView textTargetUri;
    ImageView targetImage;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //loadImage:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        Button buttonLoadImage = (Button)findViewById(R.id.LoadImage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_UPLOAD);
            }});

        Button TakePicture = (Button) findViewById(R.id.TakePicture);

        //Disable the button if the user has no camera
        if(!hasCamera())
            TakePicture.setEnabled(false);
    }

    //Check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //upload picture
        if (requestCode == REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Uri tUri = data.getData();

            String FileName= getContentResolver().getType(tUri);
            String filenameArray[] = FileName.split("\\/");
            String extension = filenameArray[filenameArray.length-1];
            if(extension.toLowerCase().equals("jpg")  || extension.toLowerCase().equals("png") || extension.toLowerCase().equals("jpeg")) {
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    targetImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(extension.toLowerCase().equals("jpeg"))
                    Toast.makeText(this, "worning! picture format is jpeg", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Image uploaded successfully :)", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Not a valid picture format :(", Toast.LENGTH_SHORT).show();
            }
        }

        //take picture
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            try {
                Long name= (System.currentTimeMillis());
                FileOutputStream out = new FileOutputStream(name.toString());
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            targetImage.setImageBitmap(photo);
            Toast.makeText(this, "Photo was taken successfully :)", Toast.LENGTH_SHORT).show();
        }
    }
}
