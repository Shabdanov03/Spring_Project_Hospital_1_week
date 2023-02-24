package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Doctor;
import peaksoft.model.Patient;
import peaksoft.repository.PatientRepository;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Repository
@Transactional
public class PatientRepImpl implements PatientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void savePatient(Patient patient) {
        try {
            entityManager.persist(patient);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Patient> getAllPatient(Long id) {
        try {
            return entityManager.createQuery("from  Patient p join p.hospital h where h.id = :id", Patient.class).
                    setParameter("id", id).getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deletePatient(Long id) {
        try {
            Patient patient = entityManager.find(Patient.class, id);
            patient.getHospital().getPatients().remove(patient);
            patient.setHospital(null);
            entityManager.remove(patient);

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Patient findByPatientId(Long id) {
        try {
          return   entityManager.find(Patient.class,id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updatePatient(Long id, Patient patient) {
        try {
            Patient oldPatient = entityManager.find(Patient.class, id);
            oldPatient.setFirstName(patient.getFirstName());
            oldPatient.setLastName(patient.getLastName());
            oldPatient.setPhoneNumber(patient.getPhoneNumber());
            oldPatient.setGender(patient.getGender());
            oldPatient.setEmail(patient.getEmail());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
