package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveHospital(Hospital hospital) {
        try {
            repository.saveHospital(hospital);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Hospital> getAllHospitals() {
        try {
            return repository.getAllHospitals();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospitals(String keyWord) {
        try {
            if (keyWord != null && !keyWord.trim().isEmpty()) {
                return repository.search(keyWord);
            } else {
                return repository.getAllHospitals();
            }
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteHospital(Long id) {
        try {
            repository.deleteHospital(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hospital findByHospitalId(Long id) {
        try {
            return repository.findByHospitalId(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateHospital(Long id, Hospital hospital) {
        try {
            repository.updateHospital(id, hospital);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
