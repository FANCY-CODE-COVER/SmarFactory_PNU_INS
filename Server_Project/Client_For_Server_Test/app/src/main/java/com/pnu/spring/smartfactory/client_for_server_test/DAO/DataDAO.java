package com.pnu.spring.smartfactory.client_for_server_test.DAO;

public class DataDAO {
    private String code;
    private String code_nm;
    private String category_id;

    public DataDAO(String code, String code_nm, String category_id, String description) {
        this.code = code;
        this.code_nm = code_nm;
        this.category_id = category_id;
        this.description = description;
    }

    private String description;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode_nm() {
        return code_nm;
    }
    public void setCode_nm(String code_nm) {
        this.code_nm = code_nm;
    }
    public String getCategory_id() {
        return category_id;
    }
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}