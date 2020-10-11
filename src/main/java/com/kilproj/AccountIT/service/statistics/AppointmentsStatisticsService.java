package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.domain.Appointment;
import com.kilproj.AccountIT.service.AppointmentsService;
import com.kilproj.AccountIT.service.ClinicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class AppointmentsStatisticsService {

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private ClinicsService clinicsService;

    /**
     * Filling rates for each month
     *
     * @param year - the year of the fillingRates
     * @return array of length 12 (each number represents a month)
     * 0 - Jan to 11 - Dec
     */
    public double[] getMonthlyFillingRates(int year) {

        double[] fillingRates = new double[12];
        double[] fillingNumbers = new double[12];
        List<Appointment> appointmentList = appointmentsService.getAllAppointments();
        for (Appointment appointment : appointmentList) {
            if (appointment.getDate().getYear() == year) {
                fillingNumbers[appointment.getDate().getMonthValue() - 1]++;
            }
        }
        for (int i = 0; i < fillingNumbers.length; i++) {
            fillingRates[i] = fillingNumbers[i] / (clinicsService.getClinic().getAverageWeekSlots()*4);
        }
        return fillingRates;
    }

    /**
     * Filling rates for weeks
     *
     * @param year - the year passed by the query
     * @return array of length that depends on number of weeks
     * per year. Gives a percentage filling compared to the number
     * of appointments slots available
     */
    public int[] getWeeklyFillingRate(int year) {

        List<Appointment> appointmentList = appointmentsService.getAllAppointments();
        LocalDate date = LocalDate.of(year, 12, 1);
        int weeksInYear = (int) IsoFields.WEEK_OF_WEEK_BASED_YEAR.rangeRefinedBy(date).getMaximum();

        int[] fillingRate = new int[weeksInYear];
        for (Appointment appointment : appointmentList) {
            LocalDate appDate = appointment.getDate();
            if (appointment.getDate().getYear() == year) {
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = appDate.get(weekFields.weekOfWeekBasedYear());
                if (fillingRate[weekNumber] == 0) {
                    fillingRate[weekNumber] = 1;
                } else {
                    int value = ++fillingRate[weekNumber];
                    fillingRate[weekNumber] = value;
                }
            }
        }
        return fillingRate;
    }

    /**
     * Filling rates a specific week
     *
     * @param year - the year passed by the query
     * @param week - the week passed by the query
     * @return the value of filling rate for a specific week.
     */
    public double getWeekFillingRate(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDate start = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, ++week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        LocalDate end = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        List<Appointment> appointments = appointmentsService.getAllAppointmentsBetweenDates(start, end);
        int numbAppointments = appointments.size();
        double fillingRate = ((double)numbAppointments)/ clinicsService.getClinic().getAverageWeekSlots();
        return fillingRate;

    }


}
