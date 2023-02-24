package peaksoft.repository;

import peaksoft.model.Department;
import peaksoft.model.Doctor;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface DoctorRepository {

    void saveDoctor(Doctor doctor);

    List<Doctor> getAllDoctors(Long id);

    void deleteDoctors(Long id);

    Doctor findByDoctorId(Long id);

    void updateDoctor(Long id, Doctor doctor);

    List<Doctor> getAllDoctors();
}
