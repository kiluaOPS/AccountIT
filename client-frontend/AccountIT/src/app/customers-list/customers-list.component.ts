import { Customer } from "./../domain/customer";
import { CustomersService } from "./../service/data/customers.service";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-customers-list",
  templateUrl: "./customers-list.component.html",
  styleUrls: ["./customers-list.component.css"]
})
export class CustomersListComponent implements OnInit {
  customers: Customer[];
  constructor(private service: CustomersService, private router: Router) {}

  ngOnInit() {
    this.service
      .executeGetAllCustomers()
      .subscribe(
        response => this.handleGetAllCustomers(response),
        error => this.handleErrors(error)
      );
  }

  requestGetCustomerById(id: number) {
    this.router.navigate([`customer/${id}`]);
  }

  requestDeleteCustomerById(id: number, i: number) {
    this.service.executeDeleteCustomerById(id).subscribe();
    this.customers.splice(i, 1);
  }

  handleGetAllCustomers(response) {
    this.customers = response;
  }

  handleCustomerById(response) {
    console.log(response);
  }

  handleErrors(response) {
    console.log(response.error.message);
  }
}
