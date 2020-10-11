import { Customer } from './../../domain/customer';
import { Injury } from 'src/app/domain/injury';
import { Appointment } from 'src/app/domain/appointment';
import { Router } from '@angular/router';
import { AppointmentsService } from './../../service/data/appointments.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-appointment-form',
  templateUrl: './appointment-form.component.html',
  styleUrls: ['./appointment-form.component.css']
})
export class AppointmentFormComponent implements OnInit {
  modelAppointment = new Appointment();
  modelInjury = new Injury();
  modelCustomer = new Customer();
  submitted = false;
  constructor(private service: AppointmentsService, private router: Router) {}
  invalid = false;

  ngOnInit() {}

  onSubmit() {
    const appointment: Appointment = this.modelAppointment;
    const injury: Injury = this.modelInjury;
    const customer: Customer = this.modelCustomer;
    customer.firstName = "Jhon ";
    customer.lastName = "Doe";
    customer.address = "G411DW";
    customer.dob = new Date();
    appointment.injury = injury;

    appointment.customer = customer;
    this.service
      .executeCreateAppointment(appointment)
      .subscribe(
        response => this.handleAppointmentCreated(response),
        error => this.handleErrors(error)
      );
  }

  handleAppointmentCreated(response) {
    console.log(response);
    this.invalid = false;
    window.location.reload();
  }

  handleErrors(response) {
    console.log(response);
    this.invalid = true;
  }

}