import { WeatherService } from "./../../service/statistics/weather-service.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-weather-cvs-load",
  templateUrl: "./weather-cvs-load.component.html",
  styleUrls: ["./weather-cvs-load.component.css"]
})
export class WeatherCvsLoadComponent implements OnInit {
  visible = false;

  constructor(private service: WeatherService) {}

  ngOnInit() {}

  getWeather() {
    this.service
      .executeGetWeatherInfo()
      .subscribe(
        response => this.handleGetWeather(response),
        error => this.handleErrors(error)
      );
  }

  handleGetWeather(response) {
    console.log(response);
    this.visible = true;
  }

  handleErrors(response) {
    console.log(response);
  }
}
