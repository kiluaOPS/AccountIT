import { Color } from "ng2-charts";
import { ChartDataSets, ChartOptions } from "chart.js";
import { Appointment } from "./../../domain/appointment";
import { element } from "protractor";
import { HttpClient } from "@angular/common/http";
import { InjuriesStatisticsService } from "./../../service/statistics/injuries-statistics.service";
import { Component, OnInit } from "@angular/core";
import { MapType } from "@angular/compiler";
import { Injury } from "src/app/domain/injury";
import { count } from "rxjs/operators";

@Component({
  selector: "app-injury-age-recovery",
  templateUrl: "./injury-age-recovery.component.html",
  styleUrls: ["./injury-age-recovery.component.css"]
})
export class InjuryAgeRecoveryComponent implements OnInit {
  dataRadar: ChartDataSets[];
  description =
    "X and Y axes correspond respectively to age range and injury. The radious of the" +
    "bubble represent instead the average number of appointments to recover;";
  labels: string[];
  dataBubble: any[];
  ages: string[];
  title = "Age influence of Injury Recover";
  colors = [
    "#f06969",
    "#f2e0ac",
    "#edf09c",
    "#c5ed85",
    "#7eed9a",
    "#81ebdb",
    "#6dbade",
    "#7d63db",
    "#eb4034",
    "#e8d22e",
    "#60d624",
    "#16f5c8",
    "#10a7c9",
    "#163aa6",
    "#771cc7",
    "#eaabeb",
    "#a81333",
    "#bfbfbf",
    "#0d0d0d",
    "#b3c6ff",
    "#ff9999"
  ];

  public options: ChartOptions;

  constructor(
    private injuriesService: InjuriesStatisticsService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.injuriesService
      .executeGetInjuriesRecoveryTimeByAge()
      .subscribe(
        response => this.handleGetInjuriesRecoveryTimeByAge(response),
        error => this.handleErrors(error)
      );
  }

  handleGetInjuriesRecoveryTimeByAge(response) {
    let mapRepresentation: { [key: string]: any[] };
    const ageMap = new Map();
    mapRepresentation = response;

    Object.keys(mapRepresentation).forEach(key => {
      const injuryMap = new Map();
      for (const value in mapRepresentation[key]) {
        if (mapRepresentation[key].hasOwnProperty(value)) {
          const injury: Injury = JSON.parse(value);
          injuryMap.set(injury, mapRepresentation[key][value]);
        }
      }
      ageMap.set(key, injuryMap);
    });
    this.mapToRelatedArrays(ageMap);
  }

  handleErrors(response) {
    console.log(response);
  }

  mapToRelatedArrays(map: Map<string, Map<Injury, number[]>>) {
    // Radar Graph arrays creation
    this.dataRadar = [];
    let isfirst = true;
    let data = [];
    this.labels = [];
    this.dataBubble = [];
    this.ages = [];
    for (const [age, injuriesStats] of map) {
      let injuryCounter = 0;
      const yValues = [];
      if (isfirst) {
        // take all the injuries from the map
        const xValues = injuriesStats.keys();
        for (const injury of xValues) {
          this.labels.push(injury.type);
        }
        isfirst = false;
      }
      for (const [injury, stats] of injuriesStats) {
        yValues.push(stats[0]);
        // Bubble data point
        const dataPoint = {
          x: Number(age),
          y: injuryCounter,
          r: stats[0].toFixed(2)
        };
        data.push(dataPoint);
        injuryCounter += 1;
      }

      this.dataRadar.push({
        data: yValues,
        label: Number(age) * 10 + "-" + (Number(age) * 10 + 10),
        hidden: true
      });
      this.dataBubble.push({
        data,
        backgroundColor: this.colors.pop(),
        label: "~" + (Number(age) * 10 + 5)
      });
      this.ages.push(
        Number(age) * 10 + "-" + (Number(age) * 10 + 10) + " years"
      );
      data = [];
    }
    this.options = {
      responsive: true,
      scales: {
        yAxes: [
          {
            ticks: {
              min: -0.5,
              max: this.labels.length - 0.5,
              callback: value => this.labels[value]
            }
          }
        ],
        xAxes: [
          {
            ticks: {
              min: -0.5,
              max: this.dataBubble.length - 0.5,
              callback: value => this.ages[value]
            }
          }
        ]
      }
    };
  }
}

// , backgroundColor: this.colors[1]
