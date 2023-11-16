import { Component, ViewChild, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import {MatDividerModule} from '@angular/material/divider';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {Router} from "@angular/router";
import { ReaddataService } from '../../services/readdata.service';

interface participation {
  id: number,
  year: number,
  city: string,
  medalsCount: number,
  athleteCount: number
}

interface Countrytype {
  id: number,
  country: string,
  participations: participation[];
}

@Component({
  selector: 'app-steven',
  templateUrl: './steven.component.html',
  styleUrl: './steven.component.scss'
})

export class StevenComponent {
 @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
 CountryDatas : Countrytype[] = [];
 countryName : string[] = [];
 countryMedal : number[] = [];
 countryNB : number = 0;
 citylist : string[] = [];
 readdata: ReaddataService = inject(ReaddataService);


 constructor(private router: Router,){

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
public pieChartData: ChartData<'pie', number[], string | string[]> = {
  labels: this.countryName,
  datasets: [
  {
    data: this.countryMedal,
  },
  ],
};
public pieChartType: ChartType = 'pie';
public pieChartPlugins = [DataLabelsPlugin];

  // events
public chartClicked(e: any): void {
 let  clicked  = 0; 
 if (e.active[0]!== undefined && e.active[0].index !== undefined){
   clicked = e.active[0].index;
   if (clicked >= 0 && clicked < this.countryName.length)
    this.changeview(clicked);
}
}


public changeview(clicked : number){
  this.router.navigate(['/country', this.countryName[clicked]]);
}


changeLabels(): void {
  console.log("il y a ", this.countryNB , " pays et ", this.citylist.length , "JO");

}

public removeSlice(): void {
  if (this.pieChartData.labels) {
    this.pieChartData.labels.pop();
  }

  this.pieChartData.datasets[0].data.pop();

  this.chart?.update();
}

changeLegendPosition(): void {
  if (this.pieChartOptions?.plugins?.legend) {
    this.pieChartOptions.plugins.legend.position =
    this.pieChartOptions.plugins.legend.position === 'left'
    ? 'top'
    : 'left';
  }

  this.chart?.render();
}

toggleLegend(): void {
  if (this.pieChartOptions?.plugins?.legend) {
    this.pieChartOptions.plugins.legend.display =
    !this.pieChartOptions.plugins.legend.display;
  }

  this.chart?.render();
}

ngOnInit() {
  let i = 0;
    this.CountryDatas = this.readdata.getAlldata();

  for(let c of this.CountryDatas){
    if (c.country){
      this.countryName.push(c.country);
      this.countryNB++;
      this.countryMedal[i] = 0;
      for  (let  event in c.participations){
        if (! this.citylist.includes(c.participations[event].city))
          this.citylist.push(c.participations[event].city);
        this.countryMedal[i] += c.participations[event].medalsCount;
      }
      i++;
 // console.log(c.country);  
    }
  }

//console.log(this.CountryDatas);  
}
}
