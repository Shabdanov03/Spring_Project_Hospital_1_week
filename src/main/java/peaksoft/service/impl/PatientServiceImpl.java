package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.model.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service

public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, HospitalRepository hospitalRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    @Override
    public void savePatient(Patient patient, Long hospitalId) {
        try {
            patient.setHospital(hospitalRepository.findByHospitalId(hospitalId));
            patientRepository.savePatient(patient);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Patient> getAllPatients(Long id) {
        try {
            return patientRepository.getAllPatient(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return null;
    }

    @Transactional
    @Override
    public void deletePatient(Long id) {
        try {
            Patient patient = patientRepository.findByPatientId(id);
            Hospital hospital = hospitalRepository.findByHospitalId(patient.getHospital().getId());
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getPatient().getId().equals(id)) {
                    appointmentRepository.deleteAppointment(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);
            patientRepository.deletePatient(id);

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Patient findByPatientId(Long id) {
        try {
            return patientRepository.findByPatientId(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return null;
    }

    @Override
    public void updatePatient(Long id, Patient patient) {
        try {
            patientRepository.updatePatient(id, patient);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
