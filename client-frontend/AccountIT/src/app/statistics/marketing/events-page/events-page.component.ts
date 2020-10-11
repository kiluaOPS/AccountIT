import { ActivatedRoute } from "@angular/router";
import { MarketingEvent } from "./../../../domain/marketing-event";
import { Component, OnInit, Input } from "@angular/core";
import { EventsService } from "src/app/service/data/events.service";

@Component({
  selector: "app-events-page",
  templateUrl: "./events-page.component.html",
  styleUrls: ["./events-page.component.css"]
})
export class EventsPageComponent implements OnInit {
  events: MarketingEvent[];
  eventSelected: number;

  constructor(
    private eventService: EventsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.eventService
      .executeGetAllEvents()
      .subscribe(
        response => this.handleGetAllEvents(response),
        error => this.handleErrors(error)
      );
  }

  handleGetAllEvents(response) {
    this.events = response;
    if (this.events.length > 0 ) {
      console.log(this.route.snapshot.params.id);
      
      if (this.route.snapshot.params.id > -1) {
        this.eventSelected = this.route.snapshot.params.id;
      }
    }
  }

  onChange(event) {
    const id = event.target.value;
    this.eventSelected = id;
  }

  handleErrors(response) {
    console.log(response);
  }
}
