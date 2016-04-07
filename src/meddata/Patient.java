/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meddata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author lk0
 */
public class Patient implements Serializable {

    private String firstName;
    private String lastName;
    private String insurance;
    private String insurancePolicy;
    private String address;
    private String telephoneNumber;
    private String emergencyContact;
    private String medicalBackground;
    private String socialSecurityNumber;
    private String maritalStatus;
    private String eMailAddress;
    private String sex;
    private int patientID;
    private Appointment nextAppointment;
    private double balance;
    private double coPay;
    private ArrayList<Visit> visits;
    private boolean hasID;

    //Create a patient object with all fields
    public Patient(String firstName, String lastName, String insurance, String insurancePolicy, Double coPay, String address,
            String telephoneNumber, String emergencyContact, String medicalBackground,
            String socialSecurityNumber, String maritalStatus, String eMailAddress, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.insurance = insurance;
        this.insurancePolicy = insurancePolicy;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.emergencyContact = emergencyContact;
        this.medicalBackground = medicalBackground;
        this.socialSecurityNumber = socialSecurityNumber;
        this.maritalStatus = maritalStatus;
        this.eMailAddress = eMailAddress;
        this.sex = sex;
        this.balance = 0;
        this.coPay = coPay;
        this.hasID = false;
        this.visits = new ArrayList();
    }

    //Create a patient object with name and phone number only
    public Patient(String firstName, String lastName, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
    }

    //Create a patient with name, phone number and next appointment
    //(usefull when adding a new patient and setting an appointment)
    public Patient(String firstName, String lastName, String telephoneNumber, Appointment nextAppointment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.nextAppointment = nextAppointment;
    }
    
      
    public String getInsurancePolicy() {
        return this.insurancePolicy;
    }
    
    public void setInsurancePolicy(String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getInsurance() {
        return this.insurance;
    }

    public String getAddress() {
        return this.address;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public String getEmergencyContact() {
        return this.emergencyContact;
    }

    public String getMedicalBackground() {
        return this.medicalBackground;
    }

    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public String getEMailAddress() {
        return this.eMailAddress;
    }

    public String getSex() {
        return this.sex;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public double getCoPay() {
        return this.coPay;
    }

    public double getBalance() {
        return this.balance;
    }

    public Appointment getNextAppointment() {
        return this.nextAppointment;
    }

    public ArrayList<Visit> getVisits() {
        return this.visits;
    }

    public void addVisit(Visit visit) {
        this.visits.add(visit);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCoPay(double coPay) {
        this.coPay = coPay;
    }

    public void settFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void settLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setMedicalBackground(String medicalBackground) {
        this.medicalBackground = medicalBackground;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setEMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPatientID(int patientID) {
        if (!this.hasID) {
            this.patientID = patientID;
        }
    }

    public void setNextAppointment(Appointment nextAppointment) {
        this.nextAppointment = nextAppointment;
    }
    
    public boolean hasID() {
        return this.hasID;
    }

}
