import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  constructor(private http: HttpClient) {}

  executeGetWeatherInfo() {
    return this.http.get('http://localhost:8080/upload/weather', { responseType: 'text' }
    );
  }

  executeGetWeatherStatistics(year: number) {
    return this.http.get(`http://localhost:8080/statistics/weather/${year}`);
  }

  executeGetWeatherSlopes(year: number) {
    return this.http.get(`http://localhost:8080/statistics/weather/super-pose/${year}`);
  }

}
