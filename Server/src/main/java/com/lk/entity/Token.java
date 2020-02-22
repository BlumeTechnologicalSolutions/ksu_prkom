package com.lk.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;


public class Token {

    private Integer Id;
    private Integer UserId;
    private String Token;
    private Timestamp Expiration;

    public Token(){}

    public Token(Integer UserId, String Token,Timestamp  Expiration){
        this.UserId = UserId;
        this.Token = Token;
        this.Expiration = Expiration;
    }

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }

    public String getToken() { return Token; }
    public void setToken(String token) { Token = token; }
    public Timestamp getExpiration() { return Expiration; }
    public void setExpiration(Timestamp expiration) { Expiration = expiration; }

    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }
}
