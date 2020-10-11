import { DatePipe } from "@angular/common";
import { Color } from "ng2-charts";
import { ChartDataSets } from "chart.js";
import {
  Component,
  OnInit,
  Input,
  SimpleChanges,
  OnChanges
} from "@angular/core";
import { WeatherService } from "src/app/service/statistics/weather-service.service";
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { SourceListMap } from 'source-list-map';

@Component({
  selector: "app-weather-slope-graph",
  templateUrl: "./weather-slope-graph.component.html",
  styleUrls: ["./weather-slope-graph.component.css"]
})
export class WeatherSlopeGraphComponent implements OnInit, OnChanges {
  @Input() year: number;
  slopeDifferencesData: number[];
  title = "K factor";
  colors: Color[] = [{ backgroundColor: "#4fdbad" }];
  slopeLabels: string[];
  options = {
    scales: {
      yAxes: [
        {
          ticks: {
            min: 0
          }
        }
      ]
    }
  };

  constructor(private service: WeatherService) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes.year.currentValue) {
      this.service
        .executeGetWeatherSlopes(changes.year.currentValue)
        .subscribe(
          response => this.handleGetWeatherSlopes(response),
          error => this.handleErrors(error)
        );
    }
  }

  handleGetWeatherSlopes(response) {
    const input: Date[] = [];
    this.slopeLabels = [];
    this.slopeDifferencesData = [];
    Object.keys(response).forEach(key => {
      input.push(new Date(key));
    });

    input.sort((a: Date, b: Date) => {
      return a.getTime() - b.getTime();
    });

    for (const date of input) {
      const datePipe = new DatePipe("en-US");
      const strDate = datePipe.transform(date, "yyyy-MM-dd");
      this.slopeLabels.push(strDate);
      this.slopeDifferencesData.push(response[strDate].toFixed(2));
    }
  }

  handleErrors(response) {
    console.log(response);
    this.slopeDifferencesData = null;
  }
}
