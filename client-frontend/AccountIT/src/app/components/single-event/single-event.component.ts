import { Label } from "ng2-charts";
import { MarketingEvent } from "./../../domain/marketing-event";
import { Customer } from "./../../domain/customer";
import { ActivatedRoute } from "@angular/router";
import {
  Component,
  OnInit,
  ViewChild,
  Input,
  OnChanges,
  SimpleChanges
} from "@angular/core";
import { EventsStatisticsService } from "src/app/service/statistics/events-statistics.service";
import { LatLng } from "@agm/core";

@Component({
  selector: "app-single-event",
  templateUrl: "./single-event.component.html",
  styleUrls: ["./single-event.component.css"]
})
export class SingleEventComponent implements OnInit, OnChanges {
  eventLatLng: any;
  markers: Marker[];
  mapType = "roadmap";
  zoom = 11;
  customers: Customer[];
  event: MarketingEvent;
  message: "No customers related to the event.";
  @Input() eventId: number;
  distance = 4000;

  constructor(private service: EventsStatisticsService) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
    if (this.eventId) {
    this.service
      .executeGetEventInfluence(changes.eventId.currentValue, this.distance/1000)
      .subscribe(
        response => this.handleGetEventInfluence(response),
        error => this.handleErrors(error)
      );
    }
  }

  handleGetEventInfluence(response) {
    for (const value in response) {
      if (response.hasOwnProperty(value)) {
        this.event = JSON.parse(value);
        this.customers = response[value];
      }
    }
    this.eventLatLng = new google.maps.LatLng(
      this.event.latitude,
      this.event.longitude
    );
    console.log(this.eventLatLng.lat());
    this.addBookmarks();
  }

  handleErrors(response) {
    console.log(response);
  }

  addBookmarks() {
    this.markers = [];
    for (const customer of this.customers) {
      let marker: Marker = {
        latLng: new google.maps.LatLng(customer.latitude, customer.longitude),
        customer
      };
      this.markers.push(marker);
    }
    // console.log(this.markers);
  }

  valueChanged(value: number) {
    this.distance = Number(value);
    this.service
      .executeGetEventInfluence(this.eventId, this.distance / 1000)
      .subscribe(
        response => this.handleGetEventInfluence(response),
        error => this.handleErrors(error)
      );
  }
}

interface Marker {
  latLng: LatLng;
  customer: Customer;
}
