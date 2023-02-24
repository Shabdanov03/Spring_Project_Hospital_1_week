package peaksoft.repository;

import peaksoft.model.Doctor;
import peaksoft.model.Patient;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface PatientRepository {
    void savePatient(Patient patient);

    List<Patient> getAllPatient(Long id);

    void deletePatient(Long id);

    Patient findByPatientId(Long id);

    void updatePatient(Long id,Patient patient);
}
