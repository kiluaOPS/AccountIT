import { ChartDataSets, ChartOptions } from "chart.js";
import { ViewChild } from "@angular/core";
import { Component, OnInit, Input } from "@angular/core";
import { BaseChartDirective, Label, Color } from "ng2-charts";

@Component({
  selector: "app-line-graph",
  templateUrl: "./line-graph.component.html",
  styleUrls: ["./line-graph.component.css"]
})
export class LineGraphComponent implements OnInit {
  @Input() lineChartData: ChartDataSets[];
  @Input() lineChartLabels: Label[];
  @Input() lineChartLegend = true;
  @Input() lineChartColors: Color[];
  @Input() lineChartOptions: (ChartOptions & { annotation: any });
  lineChartType = "line";

  @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;

  constructor() {}

  ngOnInit() {}
}
