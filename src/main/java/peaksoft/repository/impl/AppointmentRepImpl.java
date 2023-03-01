package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.PatientRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Repository
@Transactional
public class AppointmentRepImpl implements AppointmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAppointment(Appointment appointment) {
        try {
            entityManager.merge(appointment);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Appointment> getAllAppointments(Long id) {
        try {
            return entityManager.createQuery("select a from Hospital h join h.appointments a where h.id=:id  order by a.id  ", Appointment.class)
                    .setParameter("id", id).getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {
        try {
            Appointment appointment = entityManager.find(Appointment.class, id);
            entityManager.remove(appointment);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Appointment findByAppointmentId(Long id) {
        try {
            return entityManager.find(Appointment.class, id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void updateAppointment(Long id, Appointment appointment) {
        try {
            Appointment oldAppointment = entityManager.find(Appointment.class, id);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getInputDate(), format);
            oldAppointment.setDate(date);
            if (date.isBefore(LocalDate.now())){
                throw new RuntimeException();
            }
            oldAppointment.setPatient(entityManager.find(Patient.class, appointment.getPatientId()));
            oldAppointment.setDepartment(entityManager.find(Department.class, appointment.getDepartmentId()));
            oldAppointment.setDoctor(entityManager.find(Doctor.class, appointment.getDoctorId()));
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
