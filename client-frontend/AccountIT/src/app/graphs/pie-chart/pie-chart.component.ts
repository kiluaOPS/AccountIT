import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-pie-chart",
  templateUrl: "./pie-chart.component.html",
  styleUrls: ["./pie-chart.component.css"]
})
export class PieChartComponent implements OnInit {
  @Input() pieChartData: number[];
  @Input() pieChartLabels: string[];
  @Input() legend: boolean;
  @Input() title: string;
  public pieChartColors = [
    {
      backgroundColor: [
        "#f06969",
        "#f2e0ac",
        "#edf09c",
        "#c5ed85",
        "#7eed9a",
        "#81ebdb",
        "#6dbade",
        "#7d63db",
        "#eb4034",
        "#e8d22e",
        "#60d624",
        "#16f5c8",
        "#10a7c9",
        "#163aa6",
        "#771cc7",
        "#eaabeb",
        "#a81333",
        "#bfbfbf",
        "#0d0d0d",
        "#b3c6ff",
        "#ff9999"],
    },
  ];

  pieChartType = "pie";

  constructor() {}

  ngOnInit() {}
}
