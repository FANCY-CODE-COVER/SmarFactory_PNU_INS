package com.pnu.spring.smartfactory.DAO;

public class PlaceDAO {
	private String rowno, place_cd, place;
	public PlaceDAO() {
		
	}
	public PlaceDAO(String rowno,String place_cd,String place) {
		
	}
	public String getRowno() {
		return rowno;
	}

	public void setRowno(String rowno) {
		this.rowno = rowno;
	}

	public String getPlace_cd() {
		return place_cd;
	}

	public void setPlace_cd(String place_cd) {
		this.place_cd = place_cd;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
