package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Appointment;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/appointments")
public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @Autowired
    public AppointmentApi(AppointmentService appointmentService, PatientService patientService,
                          DoctorService doctorService, DepartmentService departmentService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @GetMapping("/{hospitalId}")
    public String getAllDepartments(@PathVariable("hospitalId") Long id, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments(id));
        return "appointment/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create( @PathVariable Long hospitalId,Model model) {
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute("hospitalId", hospitalId);
        return "appointment/newAppointment";
    }

    @PostMapping("/{hospitalId}/save")
    public String saveDepartment(@PathVariable Long hospitalId, @ModelAttribute("newAppointment") Appointment appointment) {
        appointmentService.saveAppointment(appointment, hospitalId);
        return "redirect:/appointments/" + hospitalId;
    }


    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long hospitalId, @PathVariable Long id, Model model) {
        model.addAttribute("appointment",appointmentService.findByAppointmentId(id));
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute("hospId", hospitalId);
        return "appointment/edit";
    }

    @PutMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable Long hospitalId,@PathVariable Long id,
                         @ModelAttribute Appointment appointment){
        appointmentService.updateAppointment(id,appointment);
        return "redirect:/appointments/"+hospitalId;
    }

    @DeleteMapping("/{hospitalId}/{appointmentId}/delete")
    public String delete(@PathVariable("appointmentId") Long appointmentId, @PathVariable("hospitalId") Long hospitalId) {
        appointmentService.deleteAppointment(appointmentId, hospitalId);
        return "redirect:/appointments/"+hospitalId;
    }


}
