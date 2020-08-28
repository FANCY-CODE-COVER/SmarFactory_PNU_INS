package com.pnu.spring.smartfactory.DAO;

public class InspectDAO {
		String req_no, facility_nm, inspect_nm,	result, user_nm
		, facility_no, insp_rst_no, emplyee_nm;
		public InspectDAO()
		{
			
		}
		
		public InspectDAO(String req_no, String facility_nm, String inspect_nm, String result, String user_nm,
				String facility_no, String insp_rst_no, String emplyee_nm) {
			super();
			this.req_no = req_no;
			this.facility_nm = facility_nm;
			this.inspect_nm = inspect_nm;
			this.result = result;
			this.user_nm = user_nm;
			this.facility_no = facility_no;
			this.insp_rst_no = insp_rst_no;
			this.emplyee_nm = emplyee_nm;
		}

		public String getReq_no() {
			return req_no;
		}

		public void setReq_no(String req_no) {
			this.req_no = req_no;
		}

		public String getFacility_nm() {
			return facility_nm;
		}

		public void setFacility_nm(String facility_nm) {
			this.facility_nm = facility_nm;
		}

		public String getInspect_nm() {
			return inspect_nm;
		}

		public void setInspect_nm(String inspect_nm) {
			this.inspect_nm = inspect_nm;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getUser_nm() {
			return user_nm;
		}

		public void setUser_nm(String user_nm) {
			this.user_nm = user_nm;
		}

		public String getFacility_no() {
			return facility_no;
		}

		public void setFacility_no(String facility_no) {
			this.facility_no = facility_no;
		}

		public String getInsp_rst_no() {
			return insp_rst_no;
		}

		public void setInsp_rst_no(String insp_rst_no) {
			this.insp_rst_no = insp_rst_no;
		}

		public String getEmplyee_nm() {
			return emplyee_nm;
		}

		public void setEmplyee_nm(String emplyee_nm) {
			this.emplyee_nm = emplyee_nm;
		}
}
