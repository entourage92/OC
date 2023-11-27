import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import {MatDividerModule} from '@angular/material/divider';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import Annotation from 'chartjs-plugin-annotation';
import {Router} from "@angular/router";
import { Countrytype } from '../../interfaces/countrytype';
import { participation } from '../../interfaces/participationtype';
import { ReaddataService } from '../../services/readdata.service';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrl: './country.component.scss'
})
export class CountryComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
  route: ActivatedRoute = inject(ActivatedRoute);
  CountryDatas : Countrytype[] = [];
  countryMedal : number[] = [];
  medalNB : number = 0;
  athleteNB : number = 0;
  citylist : string[] = [];
  countryname = this.route.snapshot.params['countryname'];
  error = true;
  readdata: ReaddataService = inject(ReaddataService);

  constructor(private router: Router,)
  {

  }

  public lineChartData: ChartConfiguration['data'] = {
    datasets: [
    {
      data: this.countryMedal,
      label: 'Series A',
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)',
      fill: 'origin',
      tension: 0,
    },
    ],
    labels: this.citylist,
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.5,
      },
    },
    plugins: {
      legend: { display: false },
      annotation: {
        annotations: [
        {
          type: 'line',
          scaleID: 'x',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            display: false,
            position: 'center',
            color: 'orange',
            content: 'LineAnno',
            font: {
              weight: 'bold',
            },
          },
        },
        ],
      },
    },
  };

  public lineChartType: ChartType = 'line';

  ngOnInit() {
    let i = 0;
    this.CountryDatas = this.readdata.getAlldata();

    for(let c of this.CountryDatas){
      if (c.country == this.countryname){
        this.error = false;
        for  (let  event of c.participations){
          this.citylist.push(event.city);
          this.medalNB += event.medalsCount;
          this.athleteNB +=event.athleteCount;
          this.countryMedal[i] = event.medalsCount;
          i++;
        }
      }
    }
    if (this.error){
      this.router.navigate(['/olympic']);
    }
  }  

}
