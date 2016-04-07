/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meddata;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author lk0
 */
public class Visit implements Serializable {

    private int patientID;
    private String patientName;
    private Calendar dateTime;
    private String doctor;
    private String complaint;
    private String cause;
    private String correction;
    private Double cost;
    private Double coPay;

    public Visit(Patient patient, Calendar dateTime, String complaint,
            String doctor) {
        this.patientID = patient.getPatientID();
        this.patientName = "" +patient.getFirstName() +" " + patient.getLastName();
        this.dateTime = dateTime;
        this.complaint = complaint;
        this.doctor = doctor;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public void setCoPay (Double coPay) {
        this.coPay = coPay;
    }
    
    public void setCause (String cause) {
        this.cause = cause;
    }
    
    public void setCorrection (String correction) {
        this.correction = correction;
    }
    
    public Calendar getTime() {
        return this.dateTime;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public String getComplaint() {
        return this.complaint;
    }

    public String getCause() {
        return this.cause;
    }

    public String getCorrection() {
        return this.correction;
    }
    
    public String getPatientName() {
        return this.patientName;
    }
    
    public int getPatientID() {
        return this.patientID;
    }
    
    public Double getCost() {
        return this.cost;
    }
    
    public Double getCoPay() {
        return this.coPay;
    }

}
