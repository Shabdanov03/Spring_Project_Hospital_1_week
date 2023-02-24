package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DoctorRepository;

import java.io.NotActiveException;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Repository
@Transactional
public class DoctorRepImpl implements DoctorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveDoctor(Doctor doctor) {
        try {
            entityManager.persist(doctor);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAllDoctors(Long id) {
        try {
            return entityManager.createQuery("from Doctor d join d.hospital h where h.id=:id", Doctor.class).
                    setParameter("id",id).getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDoctors(Long id) {
        try {
            Doctor doctor = entityManager.find(Doctor.class, id);
            doctor.getHospital().getDoctors().remove(doctor);
            doctor.setHospital(null);
            entityManager.remove(doctor);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Doctor findByDoctorId(Long id) {
        try {
            return entityManager.find(Doctor.class,id);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        try{
            Doctor oldDoctor = entityManager.find(Doctor.class, id);
            oldDoctor.setFirstName(doctor.getLastName());
            oldDoctor.setLastName(doctor.getLastName());
            oldDoctor.setPosition(doctor.getPosition());
            oldDoctor.setEmail(doctor.getEmail());
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return entityManager.createQuery("select  d from Doctor d", Doctor.class).getResultList();
    }
}
