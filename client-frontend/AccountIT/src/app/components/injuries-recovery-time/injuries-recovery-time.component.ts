import { Injury } from "src/app/domain/injury";
import { HttpClient } from "@angular/common/http";
import { InjuriesStatisticsService } from "./../../service/statistics/injuries-statistics.service";
import { Component, OnInit } from "@angular/core";
import { Color } from "ng2-charts";

@Component({
  selector: "app-injuries-recovery-time",
  templateUrl: "./injuries-recovery-time.component.html",
  styleUrls: ["./injuries-recovery-time.component.css"]
})
export class InjuriesRecoveryTimeComponent implements OnInit {
  injuries: string[] = [];
  meanTime: number[] = [];
  title = "Mean Recovery Time per Injury";
  description = "The average time to recovery for every injury is represented ad a value on the bar chart";
  max: number;
  options = {
    scales: {
      yAxes: [
        {
          ticks: {
            max: this.max,
            min: 0
          }
        }
      ]
    }
  };
  colors: Color[] = [{ backgroundColor: "#5f99f5" }];

  constructor(private injuriesService: InjuriesStatisticsService) {}

  ngOnInit() {
    this.injuriesService
      .executeGetInjuriesRecoveryTime()
      .subscribe(
        response => this.handleGetInjuriesRecoveryTime(response),
        error => this.handleErrors(error)
      );
  }

  handleGetInjuriesRecoveryTime(response) {
    const values: number[] = [];
    Object.keys(response).forEach(key => {
      const injury: Injury = JSON.parse(key);
      this.injuries.push(injury.type);
      values.push(response[key][0]).toFixed(2);
    });
    this.meanTime = values;
    this.max = this.getMax(values);
    let flag: boolean;
    for (const element of this.meanTime) {
      if (element !== 0) {
        flag = false;
        break;
      }
      flag = true;
    }
    if (flag) {
      this.meanTime = null;
    }
  }

  handleErrors(response) {
    console.log(response);
  }

  getMax(values: number[]): number {
    let max = 0;
    for (let value of values) {
      if (value > max) {
        max = value;
      }
    }

    return max;
  }
}
