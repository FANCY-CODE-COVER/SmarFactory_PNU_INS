package com.pnu.spring.smartfactory.client_for_server_test.DAO;

public class Category {
     String category_id;
    public Category(){}
    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Category(String category_id) {
        this.category_id = category_id;
    }
}
