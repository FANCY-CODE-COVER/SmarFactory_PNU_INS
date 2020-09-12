package com.pnu.spring.smartfactory.DAO;

public class InspectDAO {
		String insp_rst_no, facility_nm, inspect_nm,	result, user_nm
		, facility_no, emplyee_nm;
		String req_details, remark;
		String start_dt, end_dt;
		

		public InspectDAO()
		{
			
		}
		
		public InspectDAO(String insp_rst_no, String facility_nm, String inspect_nm, String result, String user_nm,
				String facility_no, String emplyee_nm, String req_details, String remark
				,String start_dt,String end_dt) {
			super();
			this.insp_rst_no = insp_rst_no;
			this.facility_nm = facility_nm;
			this.inspect_nm = inspect_nm;
			this.result = result;
			this.user_nm = user_nm;
			this.facility_no = facility_no;
			this.emplyee_nm = emplyee_nm;
			this.req_details=req_details;
			this.remark=remark;
			this.start_dt=start_dt; 
			this.end_dt=end_dt;
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

		public String getStart_dt() {
			return start_dt;
		}

		public void setStart_dt(String start_dt) {
			this.start_dt = start_dt;
		}

		public String getEnd_dt() {
			return end_dt;
		}

		public void setEnd_dt(String end_dt) {
			this.end_dt = end_dt;
		}

		
		public String getReq_details() {
			return req_details;
		}

		public void setReq_details(String req_details) {
			this.req_details = req_details;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
}
