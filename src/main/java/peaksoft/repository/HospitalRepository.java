package peaksoft.repository;

import peaksoft.model.Hospital;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface HospitalRepository {
    void saveHospital(Hospital hospital);

    List<Hospital> getAllHospitals();

    void deleteHospital(Long id);

    Hospital findByHospitalId(Long id);

    void updateHospital(Long id, Hospital hospital);


    List<Hospital> search(String keyWord);
}
