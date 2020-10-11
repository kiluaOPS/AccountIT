import { MarketingEvent } from "./../../domain/marketing-event";
import { Component, OnInit } from "@angular/core";
import { EventsService } from "src/app/service/data/events.service";
import { DatePipe } from "@angular/common";
import { Router } from '@angular/router';

@Component({
  selector: "app-event-form",
  templateUrl: "./event-form.component.html",
  styleUrls: ["./event-form.component.css"]
})
export class EventFormComponent implements OnInit {
  model = new MarketingEvent();
  submitted = false;
  constructor(private service: EventsService, private router: Router) {}
  invalid = false;

  ngOnInit() {}

  onSubmit() {
    const event: MarketingEvent = this.model;
    console.log(event.date);
    this.service
      .executeCreateEvent(event)
      .subscribe(
        response => this.handleEventCreated(response),
        error => this.handleErrors(error)
      );
  }

  handleEventCreated(response) {
    console.log(response);
    this.invalid = false;
    window.location.reload();
  }

  handleErrors(response) {
    console.log(response);
    this.invalid = true;
  }

}
