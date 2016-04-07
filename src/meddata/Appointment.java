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
public class Appointment implements Serializable {

    private int patientID;
    private String patientName;
    private int appointmentID;
    private Calendar time;
    private boolean confirmed;
    private String reasonForVisit;
    private String doctor;
    private boolean hasID;
    private String patientContact;

    public Appointment(Patient patient, String doctor, String reasonForVisit, Calendar time) {

        this.patientID = patient.getPatientID();
        this.time = time;
        this.confirmed = false;
        this.doctor = doctor;
        this.reasonForVisit = reasonForVisit;
        this.patientName = patient.getFirstName() + " " + patient.getLastName();
        this.hasID = false;
        this.patientContact = patient.getTelephoneNumber();
    }

    public String getContact() {
        return this.patientContact;
    }
    public int getAppointmentID() {
        return this.appointmentID;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public Calendar getTime() {
        return this.time;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public String getReasonForVisit() {
        return this.reasonForVisit;
    }

    public boolean getConfirmed() {
        return this.confirmed;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public void setAppointmentID(int appointmentID) {
        if (!this.hasID) {
            this.appointmentID = appointmentID;
        }

    }

//    public void setPatient(Patient patient) {
//        this.patient = patient;
//    }
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void confirm() {
        this.confirmed = true;
    }

    public void unConfirm() {
        this.confirmed = false;
    }

}
