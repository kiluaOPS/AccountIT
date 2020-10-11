import { Router } from '@angular/router';
import { EventsService } from './../../service/data/events.service';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {
  @Input() events: Event[];
  loadAPI: any;
  constructor(private service: EventsService, private router: Router) {}

  ngOnInit() {
    if (!this.events) {
      this.service
        .executeGetAllEvents()
        .subscribe(
          response => this.handleGetAllEvents(response),
          error => this.handleErrors(error)
        );
    }
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
    console.log(this.events);

  }

  requestGetEventById(id: number) {
    this.router.navigate([`customer/${id}`]);
  }

  requestDeleteEventById(id: number, i: number) {
    this.service.executeDeleteEventById(id).subscribe();
    this.events.splice(i, 1);
  }

  handleGetAllEvents(response) {
    this.events = response;
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
  }

  handleEventById(response) {
    console.log(response);
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
}
