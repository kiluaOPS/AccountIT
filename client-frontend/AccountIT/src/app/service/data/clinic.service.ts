import { Clinic } from './../../domain/clinic';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClinicService {

  constructor(private http: HttpClient) { }

  executeGetClinic() {
    return this.http.get<Clinic>('http://localhost:8080/clinic');
  }

  executeUpdateClinic(clinic: Clinic) {
    return this.http.put<Clinic>('http://localhost:8080/update-clinic', clinic);
  }

}
