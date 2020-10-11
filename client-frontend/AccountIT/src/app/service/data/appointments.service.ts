import { Appointment } from "./../../domain/appointment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class AppointmentsService {
  constructor(private http: HttpClient) {}

  executeGetAllAppointments() {
    return this.http.get<Appointment[]>("http://localhost:8080/appointments");
  }

  executeGetAppointmentById(id) {
    return this.http.get<Appointment>(
      `http://localhost:8080/appointment/${id}`
    );
  }

  executeDeleteAppointmentById(id) {
    return this.http.delete(`http://localhost:8080/appointments/delete/${id}`);
  }

  executeAppointmentChanges(appointment: Appointment) {
    return this.http.put<Appointment>(
      `http://localhost:8080/appointment`,
      appointment
    );
  }

  executeCreateAppointment(appointment: Appointment) {
    let httpHeaders = new HttpHeaders().set("Content-Type", "application/json");
    let options = {
      headers: httpHeaders
    };
    return this.http.post<Appointment>(
      "http://localhost:8080/appointment",
      appointment,
      options
    );
  }
}
