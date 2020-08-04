package com.am.mysecuredata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;    File myDir;
    private static final String FILE_NAME_ENC="tom_cruise_enc";
    private static final String FILE_NAME_DEC="tom_cruise";

    private String my_key="fl3kxbhiikdyl6og";
    private String my_spec_key="anbdyhj35fj7s9db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);

        myDir= new File(Environment.getExternalStorageDirectory().toString()
                        +"/savehere");
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }
    public void encrypt(View view) {
        Drawable drawable = ContextCompat.getDrawable(MainActivity.this,R.drawable.tom_cruise);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);


        InputStream  inputStream = new ByteArrayInputStream(stream.toByteArray());
        File outputFileEnc = new File(myDir,FILE_NAME_ENC);
        try{
            MyEncrypter.encrypToFile(my_key,my_spec_key,inputStream,new FileOutputStream(outputFileEnc));
            Toast.makeText(this, "Encrypted!", Toast.LENGTH_SHORT).show();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    public void decrypt(View view) {
    }
}