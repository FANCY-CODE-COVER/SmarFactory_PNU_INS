package com.pnu.spring.smartfactory.client_for_server_test.DTO;

public class FacilityDAO {
	String rowno, facility_no, facility_nm, place_cd, place, pline_nm, departmet_nm, employee_nm, state, facility_type,
			reg_nm;

	public FacilityDAO() {

	}

	public FacilityDAO(String rowno, String facility_no, String facility_nm, String place_cd, String place,
			String pline_nm, String departmet_nm, String employee_nm, String state, String facility_type,
			String reg_nm) {
		this.rowno = rowno;
		this.facility_no = facility_no;
		this.facility_nm = facility_nm;
		this.place_cd = place_cd;
		this.place = place;
		this.pline_nm = pline_nm;
		this.departmet_nm = departmet_nm;
		this.employee_nm = employee_nm;
		this.state = state;
		this.facility_type = facility_type;
		this.reg_nm = reg_nm;
	}

	public String getRowno() {
		return rowno;
	}

	public void setRowno(String rowno) {
		this.rowno = rowno;
	}

	public String getFacility_no() {
		return facility_no;
	}

	public void setFacility_no(String facility_no) {
		this.facility_no = facility_no;
	}

	public String getFacility_nm() {
		return facility_nm;
	}

	public void setFacility_nm(String facility_nm) {
		this.facility_nm = facility_nm;
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

	public String getPline_nm() {
		return pline_nm;
	}

	public void setPline_nm(String pline_nm) {
		this.pline_nm = pline_nm;
	}

	public String getDepartmet_nm() {
		return departmet_nm;
	}

	public void setDepartmet_nm(String departmet_nm) {
		this.departmet_nm = departmet_nm;
	}

	public String getEmployee_nm() {
		return employee_nm;
	}

	public void setEmployee_nm(String employee_nm) {
		this.employee_nm = employee_nm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFacility_type() {
		return facility_type;
	}

	public void setFacility_type(String facility_type) {
		this.facility_type = facility_type;
	}

	public String getReg_nm() {
		return reg_nm;
	}

	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}

}
