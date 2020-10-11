package com.kilproj.AccountIT.service;

import com.kilproj.AccountIT.Repositories.WeatherRepository;
import com.kilproj.AccountIT.domain.WeatherDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public void save(WeatherDay weatherDay) {
        weatherRepository.save(weatherDay);
    }

    public void saveAll(List<WeatherDay> weatherDays) {
        weatherRepository.saveAll(weatherDays);
    }

    public List<WeatherDay> getAllWeatherDays() {
        return (List) weatherRepository.findAll();
    }

    public List<WeatherDay> getWeatherDaysInWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDate start = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, ++week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        LocalDate end = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        List<WeatherDay> weatherDays = weatherRepository.findAllByDateBetween(start, end);
        return weatherDays;
    }

    public Double[] getWeekAvgTempetature(int year, int week) {
        List<WeatherDay> weatherDays = getWeatherDaysInWeek(year, week);
        Double[] weekAverage = new Double[]{0.0,0.0};
        int countHigh = 0;
        int countLow = 0;
        for (WeatherDay wd: weatherDays) {
//            System.out.println(wd);
            if (wd.getHighTemp() != 0) {
                weekAverage[0] += wd.getHighTemp();
                countHigh++;
            }
            if (wd.getLowTemp() != 0) {
                weekAverage[1] += wd.getLowTemp();
                countLow++;
            }
        }
        weekAverage[0] = weekAverage[0]/countHigh;
        weekAverage[1] = weekAverage[1]/countLow;
        return weekAverage;
    }
}
