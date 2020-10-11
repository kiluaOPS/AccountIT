import { CustomersStatisticsService } from "./../../service/statistics/customers-statistics.service";
import { Component, OnInit, Input } from "@angular/core";
import { Injury } from "src/app/domain/injury";

@Component({
  selector: "app-relatives-injury-graph",
  templateUrl: "./relatives-injury-graph.component.html",
  styleUrls: ["./relatives-injury-graph.component.css"]
})
export class RelativesInjuryGraphComponent implements OnInit {
  @Input() customerId: number;
  injuries: string[] = [];
  injuriesValues: number[];
  tempValue: number[] = [];
  title = "Injuries related to customer Relatives";

  constructor(private service: CustomersStatisticsService) {}

  ngOnInit() {
    this.service
      .executeGetRelativesInjuries(this.customerId)
      .subscribe(
        response => this.handleRelativesInjuries(response),
        error => this.handleErrors(error)
      );
  }

  handleRelativesInjuries(response) {
    console.log(response);

    let mapRepresentation: { [key: string]: number };
    mapRepresentation = response;
    Object.keys(mapRepresentation).forEach(key => {
      let injury: Injury = JSON.parse(key);
      console.log(key);
      console.log(JSON.parse(key));

      // console.log(injury);
      this.injuries.push(injury.type);
      this.tempValue.push(mapRepresentation[key]);
    });
    this.injuriesValues = this.tempValue;
    console.log(this.injuriesValues);
  }

  handleErrors(response) {
    console.log(response.error.message);
  }
}
