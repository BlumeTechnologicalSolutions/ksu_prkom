package com.lk.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Token {

    private Integer Id;
    private String Login;
    private String Token;
    private Date Expiration;

    public Token(){}

    public Token(String Login, String Token, Date Expiration){
        this.Login = Login;
        this.Token = Token;
        this.Expiration = Expiration;
    }

    public Integer getId() { return Id; }
    public void setId(Integer id) { Id = id; }
    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }
    public String getToken() { return Token; }
    public void setToken(String token) { Token = token; }
    public Date getExpiration() { return Expiration; }
    public void setExpiration(Date expiration) { Expiration = expiration; }

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

}
