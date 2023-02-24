package peaksoft.repository;

import peaksoft.model.Department;
import peaksoft.model.Hospital;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface DepartmentRepository {

    void saveDepartment(Department department);

    List<Department> getAllDepartments(Long id);

    void deleteDepartment(Long id);

    Department findByDepartmentId(Long id);

    void updateDepartment(Long id, Department department);
}
