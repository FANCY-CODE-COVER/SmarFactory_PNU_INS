package com.pnu.spring.smartfactory.client_for_server_test.DTO;

public class Message {
    private String refresh_token;
    private String access_token;
    private String receiver;
    private String btnname;
    private String contents;

    public Message() {
    }

    public Message(String access_token, String refresh_token, String receiver, String contents, String btnname) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.receiver = receiver;
        this.contents = contents;
        this.btnname = btnname;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setBtnname(String btnname) {
        this.btnname = btnname;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getBtnname() {
        return btnname;
    }

    public String getContents() {
        return contents;
    }
}
