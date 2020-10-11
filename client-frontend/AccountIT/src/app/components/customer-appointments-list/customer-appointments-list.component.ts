import { Router } from '@angular/router';
import { AppointmentsService } from './../../service/data/appointments.service';
import { Appointment } from './../../domain/appointment';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-customer-appointments-list',
  templateUrl: './customer-appointments-list.component.html',
  styleUrls: ['./customer-appointments-list.component.css']
})
export class CustomerAppointmentsListComponent implements OnInit {
  appointments: Appointment[];
  constructor(private service: AppointmentsService, private router: Router) {}

  ngOnInit() {
    this.service
      .executeGetAllAppointments()
      .subscribe(
        response => this.handleGetAllAppointments(response),
        error => this.handleErrors(error)
      );
  }

  requestGetAppointmentById(id: number) {
    this.router.navigate([`appointment/${id}`]);
  }

  requestDeleteAppointmentById(id: number, i: number) {
    this.service.executeDeleteAppointmentById(id).subscribe();
    // splice is for removing the customer from the table
    this.appointments.splice(i, 1);
  }

  handleGetAllAppointments(response) {
    this.appointments = response;
  }

  handleErrors(response) {
    console.log(response.error.message);
  }
}