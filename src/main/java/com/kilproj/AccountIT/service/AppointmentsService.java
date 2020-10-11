package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.DTO.AppointmentsList;
import com.kilproj.AccountIT.Repositories.AppointmentsRepository;
import com.kilproj.AccountIT.domain.AdvancedCustomer;
import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.exception.AppointmentException;
import com.kilproj.AccountIT.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private ClinicsService clinicsService;

    @Autowired
    private CustomersService customerService;

    @Autowired
    private AdvancedCustomerService advancedCustomerService;


    public void saveAll(AppointmentsList appointments) {
        appointmentsRepository.saveAll(appointments.getAppointments());
        advancedCustomerService.updateAdvancedCustomers();
    }

    public void saveAll(List<Appointment> appointments) {
        List<Appointment> appointments1 = (List) appointmentsRepository.saveAll(appointments);
        advancedCustomerService.updateAdvancedCustomers();
    }

    public void save(Appointment appointment) {
        Appointment a = appointmentsRepository.save(appointment);
        advancedCustomerService.updateAdvancedCustomers();
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        return appointments;
    }

    public Appointment getAppointmentById(String appointmentId) {
        List<Appointment> appointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        for (Appointment appointment : appointments) {
            if (appointment.getId() == Long.parseLong(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    public Appointment deleteAppointmentById(String appointmentId) throws CustomerException, AppointmentException {
        List<Appointment> appointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        for (Appointment appointment : appointments) {
            if (appointment.getId() == Long.parseLong(appointmentId)) {
                AdvancedCustomer advancedCustomer = advancedCustomerService.getAdvancedCustomer(appointment.getCustomer());
                advancedCustomer.removeAppointments(appointment, clinicsService.getClinic().getMaxNumberOfDays());
                advancedCustomerService.save(advancedCustomer);
                appointmentsRepository.delete(appointment);
                advancedCustomerService.updateAdvancedCustomers();
                return appointment;
            }
        }
        throw new AppointmentException("Appointment with id: " + appointmentId + " not found.");
    }

    public List<Appointment> getAppointmentsByCustomer(Customer customer) {
        List<Appointment> appointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        /* use AppointmentsList to have a list in chronological order
         *  - indeed addAppointmentChrono use date in the algorithm -
         */
        AppointmentsList customerAppointments = new AppointmentsList();
        for (Appointment appointment : appointments) {
            if (appointment.getCustomer() != null) {
                if (appointment.getCustomer().equals(customer)) {
                    customerAppointments.addAppointmentChrono(appointment);
                }
            }
        }
        return customerAppointments.getAppointments();
    }

    /*
     * If end date is left blank the end date is considered today
     */
    public List<Appointment> getAppointmentsBetween(LocalDate start) {
        LocalDate end = LocalDate.now();
        List<Appointment> allAppointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            if (appointment.getDate().isAfter(start) && appointment.getDate().isBefore(end)) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsBetween(LocalDate start, LocalDate end) {
        List<Appointment> allAppointments = (ArrayList<Appointment>) appointmentsRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            if (appointment.getDate().isAfter(start) && appointment.getDate().isBefore(end)) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public void updateAppointment(Appointment appointment) {
        appointmentsRepository.save(appointment);
        advancedCustomerService.updateAdvancedCustomers();
    }

    public List<Appointment> getAllAppointmentsBetweenDates(LocalDate start, LocalDate end) {

        return appointmentsRepository.findAllByDateBetween(start,end);
    }
}
