package com.kilproj.AccountIT.service.statistics;

import com.kilproj.AccountIT.service.WeatherService;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherStatisticsService {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AppointmentsStatisticsService appointmentsStatisticsService;

    public HashMap<LocalDate, List<Double>> getYearFillingRelation(int year) {
        HashMap<LocalDate, List<Double>> yearRates = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1);
        int weeks = calendar.getMaximum(Calendar.WEEK_OF_YEAR);

        for (int week = 0; week < weeks; week++) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            LocalDate weekStartDate = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
            Double[] tempsAvg = weatherService.getWeekAvgTempetature(year, week);
            Double fillingRate = appointmentsStatisticsService.getWeekFillingRate(year, week);
            if (!tempsAvg[0].isNaN() || !tempsAvg[1].isNaN()) {
                List<Double> info = new ArrayList<>();
                info.addAll(Arrays.asList(tempsAvg));
                info.add(fillingRate);
                yearRates.put(weekStartDate, info);
            }
        }

        return yearRates;
    }

    private Map<String, Object> superPosedCurves(int year) {
        Map<String, Object> data = new HashMap<>();
        HashMap<LocalDate, List<Double>> dataSet = getYearFillingRelation(year);
        List<Integer> weeks = new ArrayList<>();
        WeightedObservedPoints fillRateData = new WeightedObservedPoints();
        WeightedObservedPoints highTempData = new WeightedObservedPoints();
        List<LocalDate> dates = new ArrayList<>();
        dates.addAll(dataSet.keySet());
        for (LocalDate date : dataSet.keySet()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, date.getYear());
            calendar.set(Calendar.MONTH, date.getMonth().getValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
            weeks.add(calendar.get(Calendar.WEEK_OF_YEAR));
            fillRateData.add(calendar.get(Calendar.WEEK_OF_YEAR), (dataSet.get(date).get(2) * 100));
            highTempData.add(calendar.get(Calendar.WEEK_OF_YEAR), dataSet.get(date).get(1));
        }
        Collections.sort(weeks);
        Collections.sort(dates);
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(6);
        double[] coeffFillRate = fitter.fit(fillRateData.toList());
        double[] coeffHighTemp = fitter.fit(highTempData.toList());
        UnivariateFunction funcFillRate = new PolynomialFunction(coeffFillRate);
        UnivariateFunction functionHighTemp = new PolynomialFunction(coeffHighTemp);

        data.put("weeks", weeks);
        data.put("week-strings", dates);
        data.put("fillRate", funcFillRate);
        data.put("highTemp", functionHighTemp);
        return data;
    }



    public Map<LocalDate, Double> getCurvesKFactor(int year) {
        Map<String, Object> objects = superPosedCurves(year);
        List<Integer> weeks = (List) objects.get("weeks");
        List<LocalDate> weekString = (List) objects.get("week-strings");
        UnivariateFunction curve1 = (UnivariateFunction) objects.get("fillRate");
        UnivariateFunction curve2 = (UnivariateFunction) objects.get("highTemp");
        Integer prev = weeks.get(0);
        Map<LocalDate, Double> deltaSlopeValues = new HashMap<>();
        for (int i = 1; i < weeks.size(); i++) {
            Integer next = weeks.get(i);
            double kValue = calculateKFactor(curve1,curve2, prev, next);
            deltaSlopeValues.put(weekString.get(i),kValue);
            prev = next;
        }
        return deltaSlopeValues;
    }


    private double slope(UnivariateFunction function, int start, int end) {
        double y11 = function.value(start);
        double y12 = function.value(end);
        int deltaX = end - start;
        return (y12 - y11)/deltaX;
    }

    private double calculateKFactor(UnivariateFunction function1, UnivariateFunction function2, int start, int end) {
        double m1 = slope(function1, start, end);
        double m2 = slope(function2, start, end);
        double k = Math.abs(m1 - m2)/ (Math.abs(Math.abs(m1) - Math.abs(m2)) +1);
        return k;
    }



    /* Approach not used. Superposition of the two curves and area between the two
     * in order to obtain a parameter to compare them. K factor has been used instead
     */

//    public List<Double> getCurvesSimilarityByArea(int year) {
//        Map<String, Object> objects = superPosedCurves(year);
//        List<Integer> weeks = (List) objects.get("weeks");
//        UnivariateFunction curve1 = (UnivariateFunction) objects.get("fillRate");
//        UnivariateFunction curve2 = (UnivariateFunction) objects.get("highTempRotatedTrans");
//        Integer prev = weeks.get(0);
//        List<Double> areaValues = new ArrayList<>();
//        for (int i = 1; i < weeks.size(); i++) {
//            Integer next = weeks.get(i);
//            UnivariateIntegrator integrator = new SimpsonIntegrator();
//            double area = areaBetweenCurves(curve1,curve2, prev, next);
//            areaValues.add(area);
//            prev = next;
//        }
//        return areaValues;
//    }
//    private UnivariateFunction superPoseCurves(UnivariateFunction function1, UnivariateFunction function2, List<Integer> interval) {
//        double distance = getCurveArea(function1, interval) - getCurveArea(function2, interval);
//        double delta = 1;
//        boolean direction = distance > 0 ? true : false;
//        while (Math.abs(distance) > 0.0001) {
//            if (distance < 0) {
//                if (direction == true) {
//                    direction = !direction;
//                    delta = delta / 2;
//                }
//                double[] coeffic = ((PolynomialFunction) function2).getCoefficients();
//                coeffic[0] -= delta;
//                function2 = new PolynomialFunction(coeffic);
//            } else {
//                if (direction == false) {
//                    direction = !direction;
//                    delta = delta / 2;
//                }
//                double[] coeffic = ((PolynomialFunction) function2).getCoefficients();
//                coeffic[0] += delta;
//                function2 = new PolynomialFunction(coeffic);
//            }
//
//            distance = getCurveArea(function1, interval) - getCurveArea(function2, interval);
//        }
//        return function2;
//    }
//
//
//    private UnivariateFunction rotate(UnivariateFunction function, List<Integer> xValues) {
//        double min = Double.MIN_VALUE;
//        double[] values = new double[xValues.size()];
//        for (int i = 0; i < values.length; i++) {
//            values[i] = function.value(xValues.get(i));
//        }
//        for (int i = 0; i < values.length; i++) {
//            if (values[i] < min) {
//                min = values[i];
//            }
//        }
//        for (int i = 0; i < values.length; i++) {
//            double delta = values[i] - min;
//            values[i] = min - delta;
//        }
//
//        WeightedObservedPoints points = new WeightedObservedPoints();
//        for (int i = 0; i < values.length; i++) {
//            points.add(xValues.get(i), values[i]);
//        }
//        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(6);
//        double[] rotatedCoeff = fitter.fit(points.toList());
//        return new PolynomialFunction(rotatedCoeff);
//    }
//
//    private double getCurveArea(UnivariateFunction function, List<Integer> xValues) {
//        UnivariateIntegrator integrator = new SimpsonIntegrator();
//        return integrator.integrate(10000, function, xValues.get(0), xValues.get(xValues.size() - 1));
//    }
//
//    private double areaBetweenCurves(UnivariateFunction function1, UnivariateFunction function2, int start, int end) {
//        UnivariateIntegrator integrator = new SimpsonIntegrator();
//        double area1 = integrator.integrate(10000, function1, start, end);
//        double area2 = integrator.integrate(10000, function2, start, end);
//        return Math.abs(area1 - area2);
//    }
}
