import { DynamicScriptLoaderService } from "./../../service/scripts/dynamic-script-loader.service";
import { Router } from "@angular/router";
import { Appointment } from "../../domain/appointment";
import { AppointmentsService } from "../../service/data/appointments.service";
import {
  Component,
  OnInit,
  Input,
  ViewChild
} from "@angular/core";


@Component({
  selector: "app-appointments-list",
  templateUrl: "./appointments-list.component.html",
  styleUrls: ["./appointments-list.component.css"]
})
export class AppointmentsListComponent implements OnInit {
  @Input() appointments: Appointment[];
  @Input() message: string;
  @ViewChild("dataTable", { static: false }) table: any;
  loadAPI: any;

  constructor(
    private service: AppointmentsService,
    private router: Router,
    private loader: DynamicScriptLoaderService
  ) {}


  ngOnInit() {
    if (!this.appointments) {
      this.service
        .executeGetAllAppointments()
        .subscribe(
          response => this.handleGetAllAppointments(response),
          error => this.handleErrors(error)
        );
    }
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
  }

  requestGetAppointmentById(id: number) {
    this.router.navigate([`appointment/${id}`]);
  }

  requestDeleteAppointmentById(id: number, i: number) {
    this.service.executeDeleteAppointmentById(id).subscribe();
    this.appointments.splice(i, 1);
  }

  handleGetAllAppointments(response) {
    this.appointments = response;
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
  }

  handleErrors(response) {
    console.log(response.error.message);
  }

  loadScript() {
    let node = document.createElement("script");
    node.src = "../../../assets/js/demo/datatables-demo.js";
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }

  // loadScripts() {
  //   // You can load multiple scripts by just providing the key as argument into load method of the service
  //   this.loader
  //     .load(
  //       "datatables-demo",
  //       "jquery-datatables",
  //       "bootstrap-datatables",
  //       "jquery-easing"
  //     )
  //     .then(data => {
  //       // Script Loaded Successfully
  //     })
  //     .catch(error => console.log(error));
  // }
}
