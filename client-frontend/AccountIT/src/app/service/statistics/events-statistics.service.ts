import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class EventsStatisticsService {
  constructor(private http: HttpClient) {}

  executeGetEventInfluence(id, distance) {
    let params = new HttpParams().set("id", id).set("distance", distance);
    console.log("PARAMS--------------------");
    console.log(params);

    return this.http.get(`http://localhost:8080/statistics/event`, {
      params
    });
  }
}
