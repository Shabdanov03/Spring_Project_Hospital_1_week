package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Repository
@Transactional
public class HospitalRepImpl implements HospitalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveHospital(Hospital hospital) {
        try {
            entityManager.persist(hospital);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Hospital> getAllHospitals() {
        try {
            return entityManager.createQuery("select h from Hospital h", Hospital.class).getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteHospital(Long id) {
        try {
            Hospital hospital = entityManager.find(Hospital.class, id);
            entityManager.remove(hospital);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hospital findByHospitalId(Long id) {
        try {
            return entityManager.find(Hospital.class, id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateHospital(Long id, Hospital hospital) {
        try {
            Hospital oldHospital = entityManager.find(Hospital.class, id);
            oldHospital.setName(hospital.getName());
            oldHospital.setAddress(hospital.getAddress());
            oldHospital.setDoctors(hospital.getDoctors());
            oldHospital.setPatients(hospital.getPatients());
            oldHospital.setDepartments(hospital.getDepartments());
            oldHospital.setAppointments(hospital.getAppointments());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Hospital> search(String keyWord) {
        try {
            return entityManager.createQuery("select h from Hospital h where h.name ilike %(:keyWord)", Hospital.class)
                    .setParameter("keyWord", "%" + keyWord + "%").getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
