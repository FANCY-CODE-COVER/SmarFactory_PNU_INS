package com.example.android.glass.cardsample.data;

public class RepairDAO {
    String repair_no,facility_no, req_no, start_dt, end_dt, status,
            cause, repair_type;

    public RepairDAO() {

    }

    public RepairDAO(String repair_no, String facility_no, String req_no, String start_dt, String end_dt, String status,
                     String cause, String repair_type) {
        super();
        this.repair_no = repair_no;
        this.facility_no = facility_no;
        this.req_no = req_no;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.status = status;
        this.cause = cause;
        this.repair_type = repair_type;
    }

    public String getRepair_no() {
        return repair_no;
    }

    public void setRepair_no(String repair_no) {
        this.repair_no = repair_no;
    }

    public String getFacility_no() {
        return facility_no;
    }

    public void setFacility_no(String facility_no) {
        this.facility_no = facility_no;
    }

    public String getReq_no() {
        return req_no;
    }

    public void setReq_no(String req_no) {
        this.req_no = req_no;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getRepair_type() {
        return repair_type;
    }

    public void setRepair_type(String repair_type) {
        this.repair_type = repair_type;
    }


}
