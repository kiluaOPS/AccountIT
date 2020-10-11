import { AdvancedCustomer } from "src/app/domain/advanced-customer";
import { CustomersStatisticsService } from "./../../../service/statistics/customers-statistics.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-customers-statistics",
  templateUrl: "./customers-statistics.component.html",
  styleUrls: ["./customers-statistics.component.css"]
})
export class CustomersStatisticsComponent implements OnInit {
  constructor(private customerService: CustomersStatisticsService) {}

  ngOnInit() {}
}
