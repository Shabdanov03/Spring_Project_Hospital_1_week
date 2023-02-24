package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, HospitalRepository hospitalRepository, DepartmentRepository departmentRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    @Override
    public void saveDoctor(Doctor doctor, Long hospitalId) {
        try {
            doctor.setHospital(hospitalRepository.findByHospitalId(hospitalId));
            doctor.getDepartmentId()
                    .forEach(d -> doctor.addDepartment(departmentRepository.findByDepartmentId(d)));
            doctorRepository.saveDoctor(doctor);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAllDoctors(Long id) {
        try {
            return doctorRepository.getAllDoctors(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) {
        try {
            Doctor doctor = doctorRepository.findByDoctorId(id);
            Hospital hospital = hospitalRepository.findByHospitalId(doctor.getHospital().getId());
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().getId().equals(id)) {
                    appointmentRepository.deleteAppointment(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);
            doctorRepository.deleteDoctors(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Doctor findByDoctorId(Long id) {
        try {
            return doctorRepository.findByDoctorId(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        try {
            doctorRepository.updateDoctor(id, doctor);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}
