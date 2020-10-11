import { ClinicService } from "./../../service/data/clinic.service";
import { Router } from "@angular/router";
import { Clinic } from "./../../domain/clinic";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-clinic-form",
  templateUrl: "./clinic-form.component.html",
  styleUrls: ["./clinic-form.component.css"]
})
export class ClinicFormComponent implements OnInit {
  clinic: Clinic;
  submitted = false;
  constructor(private service: ClinicService, private router: Router) {}
  invalid = false;

  ngOnInit() {
    this.service
      .executeGetClinic()
      .subscribe(
        response => this.handleGetCustomer(response),
        error => this.handleErrors(error)
      );
  }

  onSubmit() {
    this.service
      .executeUpdateClinic(this.clinic)
      .subscribe(
        response => this.handleClinicUpdated(response),
        error => this.handleErrors(error)
      );
  }

  handleGetCustomer(response) {
    console.log(response);
    this.clinic = response;
  }

  handleClinicUpdated(response) {
    console.log(response);
    this.invalid = false;
    window.location.reload();
  }

  handleErrors(response) {
    console.log(response);
    this.invalid = true;
  }
}
