package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, HospitalRepository hospitalRepository, AppointmentRepository appointmentRepository) {
        this.departmentRepository = departmentRepository;
        this.hospitalRepository = hospitalRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    @Override
    public void saveDepartment(Department department, Long hospitalId) {
        try {
            for (Department dep : departmentRepository.getAllDepartments(hospitalId)) {
                if (dep.getName().equals(department.getName())) {
                    throw new NotFoundException(" Such a department already exists ");
                }
            }
            department.setHospital(hospitalRepository.findByHospitalId(hospitalId));
            departmentRepository.saveDepartment(department);

        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllDepartments(Long id) {
        try {
            return departmentRepository.getAllDepartments(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findByDepartmentId(id);
            for (int i = 0; i < department.getDoctors().size(); i++) {
                department.getDoctors().get(i).getDepartments().remove(department);
            }

            Hospital hospital = hospitalRepository.findByHospitalId(department.getHospital().getId());
            List<Appointment> appointments = hospital.getAppointments();
            for (Appointment appointment : appointments) {
                if (appointment.getDepartment().getId().equals(id)) {
                    appointmentRepository.deleteAppointment(appointment.getId());
                }
            }
            hospital.getAppointments().removeAll(appointments);

            departmentRepository.deleteDepartment(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Department findByDepartmentId(Long id) {
        try {
            return departmentRepository.findByDepartmentId(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDepartment(Long id, Department department) {
        try {
            departmentRepository.updateDepartment(id, department);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
