import {Component, inject, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ChartConfiguration, ChartData, ChartType} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import Annotation from 'chartjs-plugin-annotation';
import {Router} from "@angular/router";
import {OlympicService} from "../../services/olympic.service";
import {combineLatest, map, Observable} from "rxjs";

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrl: './country.component.scss'
})
export class CountryComponent {
  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;
  route: ActivatedRoute = inject(ActivatedRoute);
  countryname = this.route.snapshot.params['countryname'];
  olympic: OlympicService = inject(OlympicService);
  medalNB$: Observable<number> = this.olympic.getmedalpercountry(this.countryname);
  athleteNB$: Observable<number> = this.olympic.getathletepercountry(this.countryname);
  citylist$: Observable<Set<string>> = this.olympic.getcitypercountry(this.countryname);
  countryMedal$: Observable<number[]> = this.olympic.getmedalperevent(this.countryname);

  constructor(private router: Router) {
  }

  public lineChartData: Observable<ChartConfiguration['data']> = combineLatest(this.citylist$, this.countryMedal$).pipe(
    map(([citylist, countryMedal]) => {
      if (Array.from(citylist).length == 0 || countryMedal.length == 0 || Array.from(citylist).length != countryMedal.length)
        this.router.navigate(['/']);

      return {
        datasets: [
          {
            data: countryMedal,
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
        labels: Array.from(citylist),
      }
    }))

  public lineChartOptions: ChartConfiguration['options'] = {
    elements: {
      line: {
        tension: 0.5,
      },
    },
    plugins: {
      legend: {display: false},
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

}
