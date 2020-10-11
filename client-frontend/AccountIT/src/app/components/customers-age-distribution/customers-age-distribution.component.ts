import { CustomersStatisticsService } from "./../../service/statistics/customers-statistics.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-customers-age-distribution",
  templateUrl: "./customers-age-distribution.component.html",
  styleUrls: ["./customers-age-distribution.component.css"]
})
export class CustomersAgeDistributionComponent implements OnInit {
  ageIntervals = ["0-10", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90", "90-100", "100-110"];
  ageDistribution: number[];
  title = "Age Distribution";
  constructor(private service: CustomersStatisticsService) {}

  ngOnInit() {
    this.service
      .executeGetCustomersAgeDistribution()
      .subscribe(
        response => this.handleAgeDistribution(response),
        error => this.handleErrors(error)
      );
  }

  handleAgeDistribution(response) {
    this.ageDistribution = response;
    let flag: boolean;
    for (let element of this.ageDistribution) {
      if (element !== 0) {
        flag = false;
        break;
      }
      flag = true;
    }
    if (flag) {
      this.ageDistribution = null;
    }
  }


  handleErrors(response) {
    console.log(response.error.message);
  }
}
