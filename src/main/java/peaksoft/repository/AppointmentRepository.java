package peaksoft.repository;

import peaksoft.App;
import peaksoft.model.Appointment;
import peaksoft.model.Department;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface AppointmentRepository {
    void saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointments(Long id);

    void deleteAppointment(Long id);

    Appointment findByAppointmentId(Long id);

    void updateAppointment(Long id, Appointment appointment);
}
