package com.am.mysecuredata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MyEncrypter {
    private static final String ALGO_IMAGE_ENCRYPTOR="AES/CBC/PKCS5Padding";
    private static final String ALGO_SECRET_KEY = "AES";
    private static final int READ_WRITE_HUFFER = 1024;

    public static void encrypToFile(String keyStr,
                                    String specStr,
                                    InputStream in , OutputStream out)
            throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        try {

            IvParameterSpec iv = new IvParameterSpec(specStr.getBytes("UTF-8"));

            SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"),ALGO_SECRET_KEY);

            Cipher c = Cipher.getInstance(ALGO_IMAGE_ENCRYPTOR);

            c.init(Cipher.ENCRYPT_MODE,keySpec,iv);

            out= new CipherOutputStream(out,c);
            int count = 0 ;
            byte[] buffer = new byte[READ_WRITE_HUFFER];
            while ((count=in.read(buffer))>0){
                out.write(buffer,0,count);
            }
        } finally {
            out.close();
        }
    }
}
