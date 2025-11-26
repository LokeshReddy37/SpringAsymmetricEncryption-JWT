package com.LokeshReddy.SpringSecuityAsymmetricEncryption.Security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    private KeyUtils(){//no one can call this constructor as it is private

    }
    public static PrivateKey loadPrivateKey(final String pemPath)throws Exception{
        final String key=readKeyFromResource(pemPath).replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s+","");
        final byte[] decode= Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(decode);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);

    }
    public static PublicKey loadPublicKey(final String pemPath)throws Exception{
        final String key=readKeyFromResource(pemPath).replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s+","");
        final byte[] decode= Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(decode);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
    public static String readKeyFromResource(final String pemPath) throws Exception{
        try(final InputStream is=KeyUtils.class.getResourceAsStream(pemPath)){
            if(is==null){
                throw new IllegalArgumentException("could not find key file"+pemPath);
            }
            return new String(is.readAllBytes());
        }

    }

}
