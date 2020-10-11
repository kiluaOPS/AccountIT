import { AdvancedCustomer } from 'src/app/domain/advanced-customer';
import { AppointmentsService } from "./../../service/data/appointments.service";
import { ActivatedRoute } from "@angular/router";
import { CustomersService } from "./../../service/data/customers.service";
import { AppointmentsList } from "./../../domain/appointments-list";
import { Component, OnInit, Input, AfterViewInit } from "@angular/core";

@Component({
  selector: "app-adv-customer-appointments",
  templateUrl: "./adv-customer-appointments.component.html",
  styleUrls: ["./adv-customer-appointments.component.css"]
})
export class AdvCustomerAppointmentsComponent implements OnInit, AfterViewInit {
  @Input() appointmentsSets: AppointmentsList[];
  @Input() id: number;

  constructor(private service: CustomersService) {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    if (!this.appointmentsSets) {
      this.service
        .executeGetAdvCustomerById(this.id)
        .subscribe(response => this.handleResponse(response));
    }
  }

  handleResponse(response) {
    this.appointmentsSets = response.appointmentsSets;
    console.log("HEREEEEE" + this.appointmentsSets.length);
  }
}
