package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.SaveImageAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Laptop on 6.12.2015..
 */
public class ImagePickerActivity extends Activity {

    private ImageView imageView;
    private Bitmap selectedImage;
    private final int SELECT_PHOTO = 1;
    AlertDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        imageView = (ImageView) findViewById(R.id.imageView);
        this.progress = new ProgressDialog(this);

        // Button for picking a picture from phone gallery
        Button pickImage = (Button) findViewById(R.id.btn_pick);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/jpeg");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        // Button for saving picture
        Button saveImage = (Button) findViewById(R.id.btn_save);

        saveImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            String base64String = encodeToBase64(selectedImage);
            saveProfilePicture(base64String);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        imageView.setMaxHeight(150);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    // Function for encoding bitmap file to Base64 format
    public String encodeToBase64(Bitmap i) {
        Bitmap image = i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        // print in log base64 string for bitmap
        Log.e("LOOK", imageEncoded);

        return imageEncoded;
    }

    public void saveProfilePicture(String base64String) {
        SaveImageAsync process = new SaveImageAsync("goran@mail.com", base64String, new IListener<Void>() {

            @Override
            public void onBegin() {
                progress.setMessage("Saving picture...");
                progress.show();
            }

            @Override
            public void onFinish(WebServiceResult<Void> result) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
                Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_SHORT).show();
                if (result.status == 0) {
                    finish();
                }
            }
        });
        process.execute();
    }
}
