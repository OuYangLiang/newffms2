package com.personal.oyl.newffms.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PwdEncoder implements PasswordEncoder {
    
    private String algorithm;
    
    private static final Logger log = LoggerFactory.getLogger(PwdEncoder.class);
    
    @Override
    public String encode(CharSequence rawPassword) {
        
        try {
            return this.encodePassword(rawPassword.toString().getBytes());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        
        return this.encode(rawPassword).equals(encodedPassword);
    }
    
    private String encodePassword(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);  
      
        md.reset();  
        md.update(data);  
      
        byte[] rlt = md.digest();  
      
        StringBuffer buf = new StringBuffer();  
      
        for (int i = 0; i < rlt.length; i++) {  
            if ((rlt[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
      
            buf.append(Integer.toHexString(rlt[i] & 0xff));  
        }  
      
        return buf.toString();  
    }

    
    public String getAlgorithm() {
        return algorithm;
    }

    
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

}
