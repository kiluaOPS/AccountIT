import { DatePipe } from "@angular/common";
import { map } from "rxjs/operators";
import { ChartDataSets, ChartOptions } from "chart.js";
import { WeatherService } from "./../../service/statistics/weather-service.service";
import { Label, Color } from "ng2-charts";
import {
  Component,
  OnInit,
  Input,
  SimpleChanges,
  OnChanges,
  PipeTransform
} from "@angular/core";

@Component({
  selector: "app-weather-year-graph",
  templateUrl: "./weather-year-graph.component.html",
  styleUrls: ["./weather-year-graph.component.css"]
})
export class WeatherYearGraphComponent implements OnInit, OnChanges {
  @Input() year: number;
  dataSets: ChartDataSets[];
  labels: string[];
  options: ChartOptions & { annotation: any } = {
    responsive: true,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{}],
      yAxes: [
        {
          id: "y-axis-0",
          position: "left",
          ticks: {
            fontColor: "#ed4937",
            min: 0
          }
        },
        {
          id: "y-axis-1",
          position: "right",
          ticks: {
            fontColor: "#edb826",
            min: 0,
            max: 100
          }
        }
      ]
    },
    annotation: {}
  };
  colors: Color[] = [
    {
      // Red
      borderColor: "#ed4937",
      pointBackgroundColor: "rgba(148,159,177,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(148,159,177,0.8)"
    },
    {
      // Blue
      borderColor: "#158beb",
      pointBackgroundColor: "rgba(77,83,96,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(77,83,96,1)"
    },
    {
      // Jellow
      borderColor: "#edb826",
      pointBackgroundColor: "rgba(148,159,177,1)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgba(148,159,177,0.8)"
    }
  ];

  constructor(private service: WeatherService) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes.year.currentValue) {
      this.service
        .executeGetWeatherStatistics(changes.year.currentValue)
        .subscribe(
          response => this.handleGetWeatherStatistics(response),
          error => this.handleErrors(error)
        );
    }
  }

  handleGetWeatherStatistics(response) {
    this.labels = [];
    const minTemp = [];
    const maxTemp = [];
    const filling = [];
    this.dataSets = [];
    const input: Date[] = [];
    Object.keys(response).forEach(key => {
      const stringDate: string = key;
      input.push(new Date(stringDate));
    });

    input.sort((a: Date, b: Date) => {
      return a.getTime() - b.getTime();
    });

    for (const date of input) {
      const datePipe = new DatePipe("en-US");
      const strDate = datePipe.transform(date, "yyyy-MM-dd");
      const values = response[strDate];
      this.labels.push(strDate);
      minTemp.push(values[0].toFixed(2));
      maxTemp.push(values[1].toFixed(2));
      filling.push((values[2] * 100).toFixed(2));
    }
    this.dataSets.push({ data: maxTemp, label: "Max Temp", fill: false });
    this.dataSets.push({ data: minTemp, label: "Min Temp", fill: false });
    this.dataSets.push({
      data: filling,
      label: "Filling Rates",
      yAxisID: "y-axis-1"
    });
    console.log(maxTemp);
    
  }

  handleErrors(response) {
    console.log(response);
  }
}
