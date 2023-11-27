import { Component, ViewChild, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import {MatDividerModule} from '@angular/material/divider';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {Router} from "@angular/router";
import { OlympicService } from '../../services/olympic.service';
import { BehaviorSubject, Observable, Subscription, pipe, combineLatest, map, of, startWith, filter } from 'rxjs';

import { Countrytype } from '../../interfaces/countrytype';
import { participation } from '../../interfaces/participationtype';

@Component({
  selector: 'app-olympic',
  templateUrl: './olympic.component.html',
  styleUrl: './olympic.component.scss'
})

export class OlympicComponent {
 @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

 CountryDatas : Countrytype[] = [];
 citylist : string[] = [];
 olympic: OlympicService = inject(OlympicService);
 countryName : Observable<string[]> = this.olympic.getcountrylist();
 countryMedal : Observable<number[]> = this.olympic.getcountrymedal();
 countryNB : number = 0;
 eventlist : Observable<string[]> = this.olympic.getevents();

 public pieChartData: Observable<ChartData<'pie', number[], string | string[]>> =  combineLatest(this.countryName, this.countryMedal).pipe(
  map( ([countryName, countryMedal]) => {
   return { labels: countryName,
   datasets: [
   {
    data: countryMedal,
  },
  ],
 }}),
  )

 constructor(private router: Router){
  this.olympic.getcountrylist().subscribe(data => {
    this.countryNB = data.length;
  })
}

public pieChartOptions: ChartConfiguration['options'] = {
  responsive: true,
  plugins: {
    legend: {
      display: false,
    },
    datalabels: {
      formatter: (value: any, ctx: any) => {
        if (ctx.chart.data.labels) {
          return ctx.chart.data.labels[ctx.dataIndex];
        }
      },
      anchor: 'end',
      clamp: true,
      align: 'end'
    },
  },
};

public pieChartType: ChartType = 'pie';
public pieChartPlugins = [DataLabelsPlugin];

  // events
public chartClicked(e: any): void {
 let  clicked  = 0; 
 if (e.active[0]!== undefined && e.active[0].index !== undefined){
   clicked = e.active[0].index;
   if (clicked >= 0 && clicked < this.countryNB)
    this.changeview(clicked);
}
}


public changeview(clicked : number){
 this.olympic.getcountrylist().subscribe(data => {
   this.router.navigate(['/country', data[clicked]])
 })
}

ngOnInit() {
  var toto = this.olympic.getevents();

   toto.subscribe(data => {
    //console.log(toto);
  })
}

}
