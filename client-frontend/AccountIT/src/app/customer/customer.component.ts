import { Customer } from "./../domain/customer";
import { AppointmentsList } from "./../domain/appointments-list";
import { AdvancedCustomer } from "src/app/domain/advanced-customer";
import { CustomersService } from "./../service/data/customers.service";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-customer",
  templateUrl: "./customer.component.html",
  styleUrls: ["./customer.component.css"]
})
export class CustomerComponent implements OnInit {
  customer: Customer;
  appointmentsSets: AppointmentsList[];
  editable = false;

  constructor(
    private service: CustomersService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.service
      .executeGetCustomerById(this.route.snapshot.params.id)
      .subscribe(response => this.handleResponse(response));
  }

  handleResponse(response) {
    this.customer = response.customer;
    this.appointmentsSets = response.appointmentsSets;
    console.log(this.customer);
    console.log(this.appointmentsSets);
  }

  setEditable() {
    this.editable = true;
  }

  setBlocked() {
    this.editable = false;
    this.service
      .executeGetCustomerById(this.route.snapshot.params.id)
      .subscribe(response => this.handleResponse(response));
  }

  saveCustomerChanges() {
    this.service
      .executeCustomerChanges(this.customer)
      .subscribe();
    this.editable = false;
  }
}
