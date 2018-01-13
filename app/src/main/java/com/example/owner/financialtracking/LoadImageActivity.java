package com.example.owner.financialtracking;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class LoadImageActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_UPLOAD = 2;
    private ImageView targetImage;

    private StorageReference mStorageRef;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = database.getReference("Account");

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //loadImage:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        Button buttonLoadImage = findViewById(R.id.LoadImage);
        targetImage = findViewById(R.id.targetimage);
        mAuth=FirebaseAuth.getInstance();
        //get current user
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_UPLOAD);
            }
        });

        Button TakePicture = findViewById(R.id.TakePicture);

        //Disable the button if the user has no camera
        if (!hasCamera())
            TakePicture.setEnabled(false);

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    //Check if the user has a camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera
    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //upload picture
        if (requestCode == REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Uri tUri = data.getData();

            String FileName = getContentResolver().getType(tUri);
            String filenameArray[] = FileName.split("\\/");
            String extension = filenameArray[filenameArray.length - 1];
            if (extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png") || extension.toLowerCase().equals("jpeg")) {
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    targetImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (extension.toLowerCase().equals("jpeg"))
                    Toast.makeText(this, LoadImageActivity.this.getResources()
                            .getString(R.string.load_warn), Toast.LENGTH_SHORT).show();
                Toast.makeText(this,LoadImageActivity.this.getResources()
                        .getString(R.string.load_up_succ) , Toast.LENGTH_SHORT).show();
                uploadImage(data);
            } else {
                Toast.makeText(this, LoadImageActivity.this.getResources()
                        .getString(R.string.load_up_not_succ), Toast.LENGTH_SHORT).show();
            }
        }

        //take picture
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            try {
                Long name = (System.currentTimeMillis());
                FileOutputStream out = new FileOutputStream(name.toString());
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            targetImage.setImageBitmap(photo);
            Toast.makeText(this, LoadImageActivity.this.getResources()
                    .getString(R.string.load_take_succ) , Toast.LENGTH_SHORT).show();
            uploadImage(data);
        }
    }

    private void uploadImage(Intent data) {
        Uri file = data.getData();
        StorageReference riversRef = mStorageRef.child("users_photos/" + userID);
        if (file != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(LoadImageActivity.this.getResources()
                    .getString(R.string.load_upload));
            progressDialog.show();
            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), LoadImageActivity.this.getResources()
                                    .getString(R.string.load_up_succ), Toast.LENGTH_SHORT).show();
                            Uri picturePath = taskSnapshot.getMetadata().getDownloadUrl();
                            DatabaseReference usersRef = ref.child(userID);
                            DatabaseReference c;
                            c=usersRef.child("Picture");
                            c.setValue(picturePath.toString());
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int)progress +"%");
                }
            });
        } else {
            Toast.makeText(this, LoadImageActivity.this.getResources()
                    .getString(R.string.load_not_choos), Toast.LENGTH_SHORT).show();
        }
    }
}
