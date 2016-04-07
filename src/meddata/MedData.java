/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meddata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Locale;

/**
 *
 * @author lk0
 */
public class MedData implements Serializable {

    //Link patient name with ids.
    private Map<String, List<Integer>> patients;
    //Link appointment ID with patient ID.
    private Map<Integer, Integer> appointments;
    //Link day with appointments.
    private Map<String, List<Integer>> appointmentsByDay;
    private int patientIdCount;
    private int appointmentIdCount;
    private int visitIdCount;
    private ArrayList<String> doctors;
    private ArrayList<Visit> checkedIn = new ArrayList();

    public MedData() {
        this.patients = new HashMap<String, List<Integer>>();
        this.appointments = new HashMap<Integer, Integer>();
        this.appointmentsByDay = new HashMap<String, List<Integer>>();
        this.patientIdCount = 1;
        this.appointmentIdCount = 1;
        this.visitIdCount = 1;
        File file = new File(System.getProperty("user.dir") + "\\data");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(System.getProperty("user.dir") + "\\data\\patients");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(System.getProperty("user.dir") + "\\data\\appointments");
        if (!file.exists()) {
            file.mkdir();
        }
        this.doctors = new ArrayList();
    }

    public void checkIn(Visit visit) {
        checkedIn.add(visit);
    }
    
    public void checkOut(Visit visit) {
        checkedIn.remove(visit);
    }
    
    public ArrayList<Visit> getCheckedIn () {
        return this.checkedIn;
    }
    
    //Creates appointment, assign appointment ID and increment appointment ID.
    //Returns true if successful.
    public boolean addAppointment(Appointment appointment) {
        appointment.setAppointmentID(appointmentIdCount);
        appointmentIdCount++;
        appointments.put(appointment.getAppointmentID(), appointment.getPatientID());
        List<Integer> ids = new ArrayList();
        if (appointmentsByDay.containsKey(appointment.getTime().get(Calendar.MONTH)
                + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                + " " + appointment.getTime().get(Calendar.YEAR))) {
            ids = appointmentsByDay.get(appointment.getTime().get(Calendar.MONTH)
                    + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                    + " " + appointment.getTime().get(Calendar.YEAR));
        }
        ids.add(appointment.getAppointmentID());
        appointmentsByDay.put(appointment.getTime().get(Calendar.MONTH)
                + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                + " " + appointment.getTime().get(Calendar.YEAR), ids);
        return writeAppointmentData(appointment);
    }

    //Deletes appointment from maps and file structure.
    //returns true if successful.
    public boolean deleteAppointment(Appointment appointment) {
        List<Integer> ids = new ArrayList();
        appointments.remove(appointment.getAppointmentID());
        ids = appointmentsByDay.get(appointment.getTime().get(Calendar.MONTH)
                + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                + " " + appointment.getTime().get(Calendar.YEAR));
        ids.remove((Integer) appointment.getAppointmentID());
        if (ids.isEmpty()) {
            appointmentsByDay.remove(appointment.getTime().get(Calendar.MONTH)
                    + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                    + " " + appointment.getTime().get(Calendar.YEAR));
        } else {
            appointmentsByDay.put(appointment.getTime().get(Calendar.MONTH)
                    + " " + appointment.getTime().get(Calendar.DAY_OF_MONTH)
                    + " " + appointment.getTime().get(Calendar.YEAR), ids);
        }
        File file = new File(System.getProperty("user.dir")
                + "\\data\\appointments\\" + appointment.getAppointmentID() + ".apt");
        return file.delete();
    }

    //Update appointment information
    private boolean updateAppointmentData(Appointment appointment) {
        return writeAppointmentData(appointment);
    }

    //Get appointment by patient ID, a patient may have more than one
    //appointment, a list is returned containing appointments. list will
    //be empty if no appointments exist.
    public ArrayList<Appointment> getAppointmentByPatientID(int patientID) {
        ArrayList<Appointment> matches = new ArrayList();
        for (Entry<Integer, Integer> n : appointments.entrySet()) {
            if (n.getValue() == patientID) {
                matches.add(getAppointmentByID(n.getKey()));
            }
        }
        return matches;
    }

    //returns list of appointments for day, list may be empty.
    public ArrayList<Appointment> getAppointmentsForDay(Calendar cal) {
        ArrayList<Appointment> matches = new ArrayList();
        if (appointmentsByDay.containsKey(cal.get(Calendar.MONTH)
                + " " + cal.get(Calendar.DAY_OF_MONTH)
                + " " + cal.get(Calendar.YEAR))) {
            for (int i : appointmentsByDay.get(cal.get(Calendar.MONTH)
                    + " " + cal.get(Calendar.DAY_OF_MONTH)
                    + " " + cal.get(Calendar.YEAR))) {
                matches.add(getAppointmentByID(i));
            }
        }
        return matches;
    }

    //Get appointment by ID, returns appointment object, null if not found
    public Appointment getAppointmentByID(int appointmentID) {
        if (!appointments.containsKey(appointmentID)) {
            return null;
        }
        try {
            FileInputStream in = new FileInputStream(System.getProperty("user.dir")
                    + "\\data\\appointments\\" + appointmentID + ".apt");
            ObjectInputStream objIn = new ObjectInputStream(in);
            Appointment appointment = (Appointment) objIn.readObject();
            in.close();
            objIn.close();
            return appointment;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Write appointment, local use only
    private boolean writeAppointmentData(Appointment appointment) {
        try {
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")
                    + "\\data\\appointments\\" + appointment.getAppointmentID() + ".apt");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(appointment);
            objOut.flush();
            out.close();
            objOut.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    //add appointment
    public boolean addPatient(Patient patient) {
        List<Integer> ids = new ArrayList();
        if (!patient.hasID()) {
            patient.setPatientID(this.patientIdCount);
            patientIdCount++;
        }

        if (patients.containsKey(patient.getFirstName() + " " + patient.getLastName())) {
            ids = patients.get(patient.getFirstName() + " " + patient.getLastName());
        }
        ids.add(patient.getPatientID());
        patients.put(patient.getFirstName() + " " + patient.getLastName(), ids);
        return writePatientData(patient);
    }

    //update patient information
    public boolean updatePatientData(String firstName, String lastName, Patient modified) {
        if (patients.get(firstName + " " + lastName).size() == 1) {
            patients.remove(firstName + " " + lastName);
        } else {
            List<Integer> ids = patients.get(firstName + " " + lastName);
            ids.remove(modified.getPatientID());
            patients.put(firstName + " " + lastName, ids);

        }
        return addPatient(modified);
    }

    //write patient data, local use only
    public boolean writePatientData(Patient patient) {
        try {
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")
                    + "\\data\\patients\\" + patient.getPatientID() + ".pat");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(patient);
            objOut.flush();
            out.close();
            objOut.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //get patient by id
    public Patient getPatientByID(int patientID) {
        try {
            FileInputStream in = new FileInputStream(System.getProperty("user.dir")
                    + "\\data\\patients\\" + patientID + ".pat");
            ObjectInputStream objIn = new ObjectInputStream(in);
            Patient patient = (Patient) objIn.readObject();
            objIn.close();
            in.close();
            return patient;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Returns an array list of matches, list will be empty
    //if there is no match found
    public ArrayList<Patient> findPatientByName(String name) {
        ArrayList<Patient> matches = new ArrayList();
        for (Entry<String, List<Integer>> n : patients.entrySet()) {
            if (n.getKey().toLowerCase().contains(name.toLowerCase())) {
                for (int i : n.getValue()) {
                    matches.add(getPatientByID(i));
                }
            }
        }
        return matches;
    }
    
    public void addDoctor(String doctor) {
        doctors.add(doctor);
    }
    
    public void removeDoctor(String doctor) {
        if (doctors.contains(doctor)) {
            doctors.remove(doctor);
        }
    }
    
    public ArrayList<String> getDoctors() {
        return this.doctors;
    }

//    public static void main(String[] args) {
//        MedData data = loadMedData.get();
//        Patient a = new Patient("joe", "schmo", "123");
//        Patient b = new Patient("joe", "schmo", "125");
//        data.addPatient(a);
//        data.addPatient(b);
//        GregorianCalendar greg = new GregorianCalendar();
//        Appointment apt = new Appointment(a, "good doc", "dont feel well", greg);
//
//        System.out.println(data.addAppointment(apt));
//        System.out.println(data.getAppointmentByID(1).getPatientName());
//
//        for (Patient p : data.findPatientByName("joe")) {
//            System.out.println(p.getLastName() +p.getPatientID());
//        }
//        for (Appointment apts : data.getAppointmentByPatientID(1)) {
//            System.out.println(apts.getPatientName());
//        }
//        data.deleteAppointment(data.getAppointmentByID(1));
//        loadMedData.save(data);
//        Tutorial10 t = new Tutorial10();
//        t.paint(null);
//        Home i = new Home();
//        i.setVisible(true);
//    }
}
