import { IMarketingEvent } from "./../../domain/imarketing-event";
import { MarketingEvent } from "./../../domain/marketing-event";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class EventsService {
  constructor(private http: HttpClient) {}

  executeGetAllEvents() {
    return this.http.get<IMarketingEvent[]>("http://localhost:8080/events");
  }

  executeCreateEvent(event: MarketingEvent) {
    let httpHeaders = new HttpHeaders().set("Content-Type", "application/json");
    let options = {
      headers: httpHeaders
    };
    return this.http.post<MarketingEvent>(
      "http://localhost:8080/event",
      event,
      options
    );
  }

  executeDeleteEventById(id: number) {
    return this.http.delete(`http://localhost:8080/events/delete/${id}`);
  }
}
