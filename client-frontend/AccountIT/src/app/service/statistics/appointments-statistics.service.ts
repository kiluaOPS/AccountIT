import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentsStatisticsService {
  constructor(private http: HttpClient) {}

  executeGetMonthlyFillingRates(year: number) {
    return this.http.get<number[]>(
      `http://localhost:8080/statistics/appointments/filling-rate-monthly/${year}`
    );
  }
}

