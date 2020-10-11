import { AppointmentsService } from './../service/data/appointments.service';
import { Appointment } from './../domain/appointment';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  appointment: Appointment;
  editable = false;

  constructor(private service: AppointmentsService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.service.executeGetAppointmentById(this.route.snapshot.params.id).subscribe(response => this.handleResponse(response));
  }

  handleResponse(response) {
    this.appointment = response;
  }

  setEditable() {
    this.editable = true;
  }

  setBlocked() {
    this.editable = false;
    this.service.executeGetAppointmentById(this.route.snapshot.params.id).subscribe(response => this.handleResponse(response));
  }

  saveAppointmentsChanges() {
    this.service.executeAppointmentChanges(this.appointment).subscribe();
    this.editable = false;
  }

}
