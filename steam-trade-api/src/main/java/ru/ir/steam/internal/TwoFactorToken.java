package ru.ir.steam.internal;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TwoFactorToken {

    private static final byte[] s_rgchSteamguardCodeChars = new byte[]{(byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54,
            (byte) 55, (byte) 56, (byte) 57, (byte) 66, (byte) 67, (byte) 68, (byte) 70, (byte) 71, (byte) 72, (byte) 74,
            (byte) 75, (byte) 77, (byte) 78, (byte) 80, (byte) 81, (byte) 82, (byte) 84, (byte) 86, (byte) 87, (byte) 88, (byte) 89};

    public static String generateSteamGuardCodeForTime(String sharedSecret, long serverTime) {
        byte[] mSecret = Base64.getDecoder().decode(sharedSecret);
        String str = null;
        long j2 = serverTime / 30;
        byte[] bArr = new byte[8];
        int i = 8;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            bArr[i2] = (byte) ((int) j2);
            j2 >>>= 8;
            i = i2;
        }
        Key secretKeySpec = new SecretKeySpec(mSecret, "HmacSHA1");
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            byte[] doFinal = instance.doFinal(bArr);
            i = doFinal[19] & 15;
            int i3 = (doFinal[i + 3] & 255) | ((((doFinal[i] & 127) << 24) | ((doFinal[i + 1] & 255) << 16)) | ((doFinal[i + 2] & 255) << 8));
            byte[] bArr2 = new byte[5];
            for (i = 0; i < 5; i++) {
                bArr2[i] = s_rgchSteamguardCodeChars[i3 % s_rgchSteamguardCodeChars.length];
                i3 /= s_rgchSteamguardCodeChars.length;
            }
            return new String(bArr2);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return str;
        }
    }

}