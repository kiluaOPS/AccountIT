import { Appointment } from "./../../domain/appointment";
import { ActivatedRoute } from "@angular/router";
import { AppointmentsService } from "./../../service/data/appointments.service";
import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-single-appointment",
  templateUrl: "./single-appointment.component.html",
  styleUrls: ["./single-appointment.component.css"]
})
export class SingleAppointmentComponent implements OnInit {
  @Input() appointment: Appointment;
  editable = false;

  constructor(
    private service: AppointmentsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    if (!this.appointment) {
      this.service
        .executeGetAppointmentById(this.route.snapshot.params.id)
        .subscribe(response => this.handleResponse(response));
    }
  }

  handleResponse(response) {
    this.appointment = response;
  }

  setEditable() {
    this.editable = true;
  }

  setBlocked() {
    this.editable = false;
    this.service
      .executeGetAppointmentById(this.route.snapshot.params.id)
      .subscribe(response => this.handleResponse(response));
  }

  saveAppointmentsChanges() {
    this.service.executeAppointmentChanges(this.appointment).subscribe();
    this.editable = false;
  }
}
