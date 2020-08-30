package com.pnu.spring.smartfactory.client_for_server_test.DTO;

public class ResponseDAO {
	private String message;

	ResponseDAO(){
	}
	ResponseDAO(String message){
		this.message=message;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
