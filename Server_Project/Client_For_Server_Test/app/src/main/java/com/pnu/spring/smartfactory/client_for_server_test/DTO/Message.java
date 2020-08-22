package com.pnu.spring.smartfactory.client_for_server_test.DTO;

public class Message {
    private String refresh_token;
    private String access_token;
    private String receiver;

    public Message() {
    }

    public Message(String access_token, String refresh_token, String receiver) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.receiver = receiver;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setReceiver(String receiver){ this.receiver=receiver;}

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getReceiver(){return receiver;}
}
