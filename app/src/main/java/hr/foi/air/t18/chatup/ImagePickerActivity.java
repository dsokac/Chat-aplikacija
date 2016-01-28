package hr.foi.air.t18.chatup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sw926.imagefileselector.ImageCropper;
import com.sw926.imagefileselector.ImageFileSelector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.MainAsync.SaveImageAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Laptop on 6.12.2015..
 */
public class ImagePickerActivity extends AppCompatActivity implements View.OnClickListener, SaveAttachments {

    Button fromSdCard, saveImage;
    private ImageFileSelector mImageFileSelector;
    private ImageCropper mImageCropper;
    private ImageView imageView;
    private Bitmap selectedImage;
    File mCurrentSelectFile;
    AlertDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromSdCard = (Button) findViewById(R.id.btn_pick);
        fromSdCard.setOnClickListener(this);

        saveImage = (Button) findViewById(R.id.btn_save);
        saveImage.setOnClickListener(this);

        ImageFileSelector.setDebug(true);

        imageView = (ImageView) findViewById(R.id.imageView);

        if (!ChatUpPreferences.getDefaults("UserProfilePictureBase64", getApplicationContext()).isEmpty()) {
            String profilePictureInBase64 = ChatUpPreferences.getDefaults("UserProfilePictureBase64", getApplicationContext());
            byte[] decodedByte = Base64.decode(profilePictureInBase64, Base64.NO_WRAP | Base64.URL_SAFE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            imageView.setImageBitmap(bitmap);
        }

        this.progress = new ProgressDialog(this);

        mImageFileSelector = new ImageFileSelector(this);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(final String file) {
                if (!TextUtils.isEmpty(file)) {
                    loadImage(file);
                    mCurrentSelectFile = new File(file);
                    if (mCurrentSelectFile != null) {
                        mImageCropper.setOutPut(150, 150);
                        mImageCropper.setOutPutAspect(1, 1);
                        mImageCropper.cropImage(mCurrentSelectFile);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "select image file error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "select image file error", Toast.LENGTH_LONG).show();
            }
        });

        mImageCropper = new ImageCropper(this);
        mImageCropper.setCallback(new ImageCropper.ImageCropperCallback() {
            @Override
            public void onCropperCallback(ImageCropper.CropperResult result, File srcFile, File outFile) {
                mCurrentSelectFile = null;
                if (result == ImageCropper.CropperResult.success) {
                    loadImage(outFile.getPath());
                } else if (result == ImageCropper.CropperResult.error_illegal_input_file) {
                    Toast.makeText(getApplicationContext(), "input file error", Toast.LENGTH_LONG).show();
                } else if (result == ImageCropper.CropperResult.error_illegal_out_file) {
                    Toast.makeText(getApplicationContext(), "output file error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadImage(final String file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeFile(file);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
        mImageCropper.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
        mImageCropper.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
        mImageCropper.onRestoreInstanceState(savedInstanceState);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pick: {
                mImageFileSelector.selectImage(this);
                break;
            }
            case R.id.btn_save: {
                if (selectedImage != null) {
                    String base64String = encodeToBase64(selectedImage);
                    saveAttachment(base64String);
                }
                break;
            }
        }
    }

    // Function for encoding bitmap file to Base64 format
    public String encodeToBase64(Bitmap i) {
        Bitmap image = i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP | Base64.URL_SAFE);

        // print in log base64 string for bitmap
        Log.e("BASE64", imageEncoded);

        return imageEncoded;
    }

    @Override
    public void saveAttachment(final String base64String) {

        String userMail = ChatUpPreferences.getDefaults("UserEmail", getApplicationContext());
        Log.e("Base64 length", Integer.toString(base64String.length()));

        if (userMail != null) {
            SaveImageAsync process = new SaveImageAsync(userMail, base64String, new IListener<Void>() {


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
                        ChatUpPreferences.setDefaults(
                                "UserProfilePictureBase64",
                                base64String,
                                getApplicationContext()
                        );
                        finish();
                    }
                }
            });
            process.execute();
        } else {
            Log.w("USER_MAIL", "User mail is null from Shared Preferences!");
        }
    }
}
