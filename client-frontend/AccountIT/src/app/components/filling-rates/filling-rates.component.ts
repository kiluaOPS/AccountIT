import { Color } from 'ng2-charts';
import { Router } from "@angular/router";
import { AppointmentsStatisticsService } from "./../../service/statistics/appointments-statistics.service";
import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-filling-rates",
  templateUrl: "./filling-rates.component.html",
  styleUrls: ["./filling-rates.component.css"]
})
export class FillingRatesComponent implements OnInit {
  // @Input() year: number;
  fillingRates: number[];
  description = "These values refer to the filling rates of the clinic by using the value of 'Average week slots' provided" + 
  " by the clinic and modifiable in settings" ;
  selected: number;
  title = "Filling Rates";
  yearValues: number[] = [2018, 2019];
  colors: Color[] = [{ backgroundColor: "#4fdbad" }];
  fillingLabels = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sept",
    "Oct",
    "Nov",
    "Dec"
  ];
  options = {
    scales: {
      yAxes: [
        {
          ticks: {
            max: 1,
            min: 0
          }
        }
      ]
    }
  };

  constructor(
    private service: AppointmentsStatisticsService
  ) {}

  ngOnInit() {
    this.service
      .executeGetMonthlyFillingRates(this.yearValues[0])
      .subscribe(
        response => this.handleGetMonthlyFillingRates(response),
        error => this.handleErrors(error)
      );
    this.selected = this.yearValues[0];
  }

  handleGetMonthlyFillingRates(response) {
    this.fillingRates = response;
    let flag: boolean;
    for (let element of this.fillingRates) {
      if (element !== 0) {
        flag = false;
        break;
      }
      flag = true;
    }
    if (flag) {
      this.fillingRates = null;
    }
    console.log(this.fillingRates);
  }

  handleErrors(response) {
    console.log(response);
  }

  onChange(year) {
    console.log(year);
    
    this.service
      .executeGetMonthlyFillingRates(year)
      .subscribe(
        response => this.handleGetMonthlyFillingRates(response),
        error => this.handleErrors(error)
      );
  }
}
