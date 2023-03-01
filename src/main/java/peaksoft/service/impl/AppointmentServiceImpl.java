package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DepartmentRepository departmentRepository, DoctorRepository doctorRepository, HospitalRepository hospitalRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Transactional
    @Override
    public void saveAppointment(Appointment appointment, Long hospitalId) {
        try {
            Appointment oldAppointment = new Appointment();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getInputDate(), formatter);
            oldAppointment.setDate(date);

            if (date.isBefore(LocalDate.now())){
                throw new RuntimeException();
            }
            oldAppointment.setPatient(patientRepository.findByPatientId(appointment.getPatientId()));
            oldAppointment.setDepartment(departmentRepository.findByDepartmentId(appointment.getDepartmentId()));
            oldAppointment.setDoctor(doctorRepository.findByDoctorId(appointment.getDoctorId()));
            hospitalRepository.findByHospitalId(hospitalId).getAppointments().add(oldAppointment);
            appointmentRepository.saveAppointment(oldAppointment);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Appointment> getAllAppointments(Long id) {
        try {
            return appointmentRepository.getAllAppointments(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return  null;
    }

    @Transactional
    @Override
    public void deleteAppointment(Long id, Long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
            if (hospital.getAppointments() != null) {
                for (int i = 0; i < hospital.getAppointments().size(); i++) {
                    if (hospital.getAppointments().get(i).getId().equals(id)) {
                        hospital.getAppointments().remove(hospital.getAppointments().get(i));
                    }
                }
            }
            appointmentRepository.deleteAppointment(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Appointment findByAppointmentId(Long id) {
        try {
            return appointmentRepository.findByAppointmentId(id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }return null;
    }

    @Override
    public void updateAppointment(Long id, Appointment appointment) {
        try {
            appointmentRepository.updateAppointment(id, appointment);
        }catch ( NotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
