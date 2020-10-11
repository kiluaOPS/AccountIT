import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class InjuriesStatisticsService {
  constructor(private http: HttpClient) {}

  executeGetInjuriesRecoveryTime() {
    return this.http.get(
      'http://localhost:8080/statistics/injuries-recovery-time'
    );
  }

  executeGetInjuriesRecoveryTimeByAge() {
    return this.http.get(
      'http://localhost:8080/statistics/injuries-recovery-time/age'
    );
  }
}
