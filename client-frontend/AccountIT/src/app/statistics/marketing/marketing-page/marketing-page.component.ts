import { Customer } from "./../../../domain/customer";
import { HttpClient } from "@angular/common/http";
import { CustomersStatisticsService } from "./../../../service/statistics/customers-statistics.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-marketing-page",
  templateUrl: "./marketing-page.component.html",
  styleUrls: ["./marketing-page.component.css"]
})
export class MarketingPageComponent implements OnInit {
  customers: Customer[];

  constructor(private customerService: CustomersStatisticsService) {}

  ngOnInit() {
    this.customerService
      .executeGetDissatifiedCustomer()
      .subscribe(
        response => this.handleGetDissatisfiedCustomer(response),
        error => this.handleErrors(error)
      );
  }

  handleGetDissatisfiedCustomer(response) {
    this.customers = response;
    if (this.customers.length < 1) {
      this.customers = undefined;
    }
  }

  handleErrors(response) {
    console.log(response.error.message);
  }
}
