import { Component, OnInit, Input } from "@angular/core";
import { RadialChartOptions, ChartDataSets, ChartType } from "chart.js";
import { Label } from "ng2-charts";

@Component({
  selector: "app-radar-graph",
  templateUrl: "./radar-graph.component.html",
  styleUrls: ["./radar-graph.component.css"]
})
export class RadarGraphComponent implements OnInit {
  public radarChartOptions: RadialChartOptions = {
    responsive: true
  };
  @Input() radarChartLabels: Label[];

  @Input() radarChartData: ChartDataSets[];

  @Input() radarTitle: string;


  public radarChartType: ChartType = 'radar';

  constructor() {}

  ngOnInit() {}

  // events
  public chartClicked({
    event,
    active
  }: {
    event: MouseEvent;
    active: {}[];
  }): void {
    console.log(event, active);
  }

  public chartHovered({
    event,
    active
  }: {
    event: MouseEvent;
    active: {}[];
  }): void {
    console.log(event, active);
  }
}
