package inc.app.mes.DTO;

public class FRequestDAO {
	String req_no, user_nm, facility_no, insp_rst_no, status, employee_nm;

	public FRequestDAO() {

	}

	public FRequestDAO(String req_no, String user_nm, String facility_no, String insp_rst_no, String status,
                       String employee_nm) {
		this.req_no = req_no;
		this.user_nm = user_nm;
		this.facility_no = facility_no;
		this.insp_rst_no = insp_rst_no;
		this.status = status;
		this.employee_nm = employee_nm;
	}

	public String getReq_no() {
		return req_no;
	}

	public void setReq_no(String req_no) {
		this.req_no = req_no;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployee_nm() {
		return employee_nm;
	}

	public void setEmployee_nm(String employee_nm) {
		this.employee_nm = employee_nm;
	}
}
