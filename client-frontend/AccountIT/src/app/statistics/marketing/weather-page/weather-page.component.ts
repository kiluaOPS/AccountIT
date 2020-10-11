import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-weather-page",
  templateUrl: "./weather-page.component.html",
  styleUrls: ["./weather-page.component.css"]
})
export class WeatherPageComponent implements OnInit {
  years: number[] = [2018, 2019, 2020];
  selectedYear: number;
  constructor() {}

  ngOnInit() {}

  onChange(event) {
    const index = event.target.selectedIndex;
    this.selectedYear = this.years[index - 1];
  }
}
