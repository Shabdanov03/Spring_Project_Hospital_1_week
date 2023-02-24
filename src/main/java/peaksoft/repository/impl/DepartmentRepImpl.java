package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DepartmentRepository;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Repository
@Transactional
public class DepartmentRepImpl implements DepartmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveDepartment(Department department) {
        try {
            entityManager.persist(department);
        }catch (NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllDepartments(Long id) {
        try {
            return entityManager.createQuery("from Department d join d.hospital h where h.id=:id", Department.class)
                    .setParameter("id", id).getResultList();
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDepartment(Long id) {
        try {
            Department department = entityManager.find(Department.class, id);
            entityManager.remove(department);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Department findByDepartmentId(Long id) {
        try {
            return entityManager.find(Department.class, id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateDepartment(Long id, Department department) {
        try {
            Department oldDepartment = entityManager.find(Department.class, id);
            oldDepartment.setName(department.getName());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
