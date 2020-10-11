import { Customer } from "./../../domain/customer";
import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Injury } from "src/app/domain/injury";

@Injectable({
  providedIn: "root"
})
export class CustomersStatisticsService {
  constructor(private http: HttpClient) {}

  executeGetCustomersAgeDistribution() {
    return this.http.get<number[]>(
      "http://localhost:8080/statistics/customers/age-distribution"
    );
  }

  executeGetReferralInfluence() {
    return this.http.get(
      "http://localhost:8080/statistics/customers/ref-influence"
    );
  }

  executeGetRelativesInjuries(id: number) {
    return this.http.get(
      `http://localhost:8080/statistics/customer/relatives-injuries/${id}`
    );
  }

  executeGetDissatifiedCustomer() {
    return this.http.get<Customer[]>(
      "http://localhost:8080/statistics/dissatisfied-customers"
    );
  }
}
