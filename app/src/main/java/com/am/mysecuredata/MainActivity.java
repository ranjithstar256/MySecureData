package com.am.mysecuredata;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    File myDir;
    private static final String FILE_NAME_ENC = "tom_cruise_enc";
    private static final String FILE_NAME_DEC = "tom_cruise";

    private String my_key = "fl3kxbhiikdyl6og";
    private String my_spec_key = "anbdyhj35fj7s9db";

    String encrypted;
    String decrypted;
    EditText editText;
    Drawable drawable;
    BitmapDrawable bitmapDrawable;
    Bitmap bitmap;
    ByteArrayOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editTextTextPersonName);
        File sourcePath = Environment.getExternalStorageDirectory();
        File path = new File(sourcePath + "/" + "savehere" + "/");
        path.mkdir();


        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    public void encrypt(View view) {
        myDir = new File(Environment.getExternalStorageDirectory().toString() + "/savehere");

        drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.tom_cruise);
        bitmapDrawable = (BitmapDrawable) drawable;
        bitmap = bitmapDrawable.getBitmap();
        stream = new ByteArrayOutputStream();
        // example.txt

        InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
        File outputFileEnc = new File(myDir, FILE_NAME_ENC);
        try {
            MyEncrypter.encrypToFile(my_key, my_spec_key, inputStream, new FileOutputStream(outputFileEnc));
            Toast.makeText(this, "Encrypted!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decrypt(View view) {
        myDir = new File(Environment.getExternalStorageDirectory().toString() + "/savehere");

        File outputFileDec = new File(myDir, FILE_NAME_DEC);
        File encFile = new File(myDir, FILE_NAME_ENC);
        try {
            MyEncrypter.decrypToFile(my_key, my_spec_key,
                    new FileInputStream(encFile), new FileOutputStream(outputFileDec));
            ;
            try (FileOutputStream out = new FileOutputStream(outputFileDec)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView.setImageURI(Uri.fromFile(outputFileDec));

            Toast.makeText(this, "Decrypted!", Toast.LENGTH_SHORT).show();

            //outputFileDec.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void encStr(View view) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String d = editText.getText().toString();


        encrypted = MyEncrypter.encrypt(d, MainActivity.this);
        Toast.makeText(this, "" + encrypted, Toast.LENGTH_SHORT).show();

    }

    public void decStr(View view) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        decrypted = MyEncrypter.decrypt(encrypted);
        Toast.makeText(this, "" + decrypted, Toast.LENGTH_SHORT).show();
    }
}