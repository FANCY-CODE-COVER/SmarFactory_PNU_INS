package com.pnu.spring.smartfactory.client_for_server_test.DTO;

public class TokenManager {
    private String refresh_token;
    private String access_token;
    private String expiresIn;

    public TokenManager() {
    }

    public TokenManager(String access_token, String refresh_token, String expiresIn) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expiresIn=expiresIn;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }


    public String getExpiresIn() {
        return expiresIn;
    }



}
