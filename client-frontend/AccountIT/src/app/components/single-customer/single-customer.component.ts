import { Customer } from "./../../domain/customer";
import { AppointmentsList } from "./../../domain/appointments-list";
import { CustomersService } from "./../../service/data/customers.service";
import { ActivatedRoute } from "@angular/router";
import { Component, OnInit, Input } from "@angular/core";
import { Appointment } from "src/app/domain/appointment";

@Component({
  selector: "app-single-customer",
  templateUrl: "./single-customer.component.html",
  styleUrls: ["./single-customer.component.css"]
})
export class SingleCustomerComponent implements OnInit {
  customerId: string;
  customer: Customer;
  appointmentsSets: AppointmentsList[];
  @Input() appointments: Appointment[];
  editable = false;

  constructor(
    private service: CustomersService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    if (!this.appointments) {
      this.service
        .executeGetAdvCustomerById(this.route.snapshot.params.id)
        .subscribe(response => this.handleResponse(response));
    }
  }

  handleResponse(response) {
    // console.log(JSON.stringify(response.customer));
    this.customer = response.customer;
    this.customerId = response.customer.id;
    this.appointmentsSets = response.appointmentsSets;
    this.appointments = response.appointments;
    console.log(
      "Number of appointments for this customer are: " +
        this.appointments.length
    );
  }

  setEditable() {
    this.editable = true;
  }

  setBlocked() {
    this.editable = false;
    this.service
      .executeGetAdvCustomerById(this.route.snapshot.params.id)
      .subscribe(response => this.handleResponse(response));
  }

  saveCustomerChanges() {
    this.service.executeCustomerChanges(this.customer).subscribe();
    this.editable = false;
  }
}
