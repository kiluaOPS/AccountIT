import { Input } from '@angular/core';
import { ChartDataSets } from 'chart.js';
import { ChartType } from 'chart.js';
import { ChartOptions } from 'chart.js';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-bubble-chart',
  templateUrl: './bubble-chart.component.html',
  styleUrls: ['./bubble-chart.component.css']
})
export class BubbleChartComponent implements OnInit {

  @Input() bubbleChartOptions: ChartOptions;
  @Input() description: string;
  public bubbleChartType: ChartType = 'bubble';
  public bubbleChartLegend = false;
  @Input() bubbleChartData: ChartDataSets[];
  @Input() bubbleTitle: string;

  constructor() { }

  ngOnInit() {
  }

}
