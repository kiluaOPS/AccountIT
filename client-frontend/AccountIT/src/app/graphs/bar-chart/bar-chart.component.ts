import { Color } from "ng2-charts";
import { Component, OnInit, Input } from "@angular/core";
import { ChartOptions } from "chart.js";

@Component({
  selector: "app-bar-chart",
  templateUrl: "./bar-chart.component.html",
  styleUrls: ["./bar-chart.component.css"]
})
export class BarChartComponent implements OnInit {
  @Input() barChartData: number[];
  @Input() barChartLabels: string[];
  @Input() options: ChartOptions;
  @Input() title: string;
  @Input() barChartColors: Color[];
  @Input() description: string;
  legend = false;

  barChartType = "bar";

  constructor() {}

  ngOnInit() {}
}
