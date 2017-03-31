package com.dentalcareapp.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dentalcareapp.firstopiniondentist.R;
import com.dentalcareapp.network.NetworkManager;
import com.dentalcareapp.network.NetworkRequest;
import com.dentalcareapp.storage.UserPrefs;

/**
 * Created by jasmeetsingh on 7/2/16.
 */

public class PostTipActivity extends AppCompatActivity {

    private EditText titleField;
    private EditText detailField;
    private Button submitButton;
    private ImageView tipImage;
    private Uri initialURI;

    private   int ACTION_REQUEST_GALLERY = 100;
    private  int  ACTION_REQUEST_CAMERA = 101;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tip);
        titleField = (EditText)findViewById(R.id.tip_title_edit);
        detailField = (EditText)findViewById(R.id.tip_detail_edit);
        tipImage = (ImageView)findViewById(R.id.tip_item_image);
        submitButton = (Button)findViewById(R.id.tip_post_button);

        tipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickerDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDentalTip();
            }
        });


    }

    public void postDentalTip(){
        String title = titleField.getText().toString();
        String desc = detailField.getText().toString();
        Uri image = initialURI;

        if ( title != null) {
            NetworkManager.getInstance().postTip(UserPrefs.getInstance().getEmail(),UserPrefs.getInstance().getPassword(),title, desc, image, new NetworkRequest() {
                @Override
                public Object success(Object data) {
                    Toast.makeText(getApplicationContext(),"Your tip is succesfully submited",Toast.LENGTH_SHORT).show();
                    finish();
                    return null;
                }

                @Override
                public Object failed(Object data) {
                    Toast.makeText(getApplicationContext(),"Failed to submit the tips",Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please add title to publish Tip",Toast.LENGTH_SHORT).show();

        }


    }


    public void imagePickerDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PostTipActivity.this);
        builder.setTitle("Select Image");
        builder.setItems(new CharSequence[] {"Gallery"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                // GET IMAGE FROM THE GALLERY
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");

                                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                                startActivityForResult(chooser, ACTION_REQUEST_GALLERY);

                                break;

//                            case 1:
//                                Intent getCameraImage = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//                                File cameraFolder;
//
//                                if (android.os.Environment.getExternalStorageState().equals
//                                        (android.os.Environment.MEDIA_MOUNTED))
//                                    cameraFolder = new File(android.os.Environment.getExternalStorageDirectory(),
//                                            "detalcareapp/");
//
//                                else
//                                    cameraFolder= PostTipActivity.this.getCacheDir();
//                                if(!cameraFolder.exists())
//                                    cameraFolder.mkdirs();
//
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//                                String timeStamp = dateFormat.format(new Date());
//                                String imageFileName = "picture_" + timeStamp + ".jpg";
//
//                                File photo = new File(Environment.getExternalStorageDirectory(),
//                                        "detalcareapp/" + imageFileName);
//                                getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
//                                initialURI = Uri.fromFile(photo);;
//                                startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);
//
//                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if (resultCode == RESULT_OK)    {
            switch (requestCode) {
                case 100:
                    initialURI = data.getData();
                    tipImage.setImageURI(initialURI);
                    tipImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;


            }

        }
    };

}
