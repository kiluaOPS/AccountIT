import { map } from "rxjs/operators";
import { CustomersStatisticsService } from "./../../service/statistics/customers-statistics.service";
import { Component, OnInit } from "@angular/core";
import { stringify } from "querystring";

@Component({
  selector: "app-referral-influence",
  templateUrl: "./referral-influence.component.html",
  styleUrls: ["./referral-influence.component.css"]
})
export class ReferralInfluenceComponent implements OnInit {
  typeReferral: string[] = [];
  numberReferral: number[] = [];
  value: number[];
  title = "Referral Influence";
  constructor(private service: CustomersStatisticsService) {}

  ngOnInit() {
    this.service
      .executeGetReferralInfluence()
      .subscribe(
        response => this.handleReferralInfluence(response),
        error => this.handleErrors(error)
      );
  }

  handleReferralInfluence(response) {
    let mapRepresentation: { [key: string]: number };
    mapRepresentation = response;
    Object.keys(mapRepresentation).forEach(key => {
      this.typeReferral.push(key);
      this.numberReferral.push(mapRepresentation[key]);
    });

    this.value = this.numberReferral;
    let flag: boolean;
    for (let element of this.value) {
      if (element !== 0) {
        flag = false;
        break;
      }
      flag = true;
    }
    if (flag) {
      this.value = null;
    }
  }

  handleErrors(response) {
    console.log(response.error.message);
  }
}
