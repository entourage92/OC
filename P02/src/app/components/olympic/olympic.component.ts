import {Component, ViewChild, OnInit, inject, OnDestroy} from '@angular/core';
import {ChartConfiguration, ChartData, ChartType} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import {Router} from "@angular/router";
import {OlympicService} from '../../services/olympic.service';
import {Observable, Subscription, combineLatest, map} from 'rxjs';

@Component({
  selector: 'app-olympic',
  templateUrl: './olympic.component.html',
  styleUrl: './olympic.component.scss'
})

export class OlympicComponent implements OnDestroy {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  olympic: OlympicService = inject(OlympicService);
  countryName$: Observable<string[]> = this.olympic.getcountrylist();
  countryMedal$: Observable<number[]> = this.olympic.getcountrymedal();
  countryNB: number = 0;
  eventlist$: Observable<Set<string>> = this.olympic.getevents();
  subscriptions: Subscription[] = [];

  public pieChartData: Observable<ChartData<'pie', number[], string | string[]>> = combineLatest(this.countryName$, this.countryMedal$).pipe(
    map(([countryName, countryMedal]) => {
        return {
          labels: countryName,
          datasets: [{data: countryMedal}],
        }
      }
    )
  )

  constructor(private router: Router) {
    var subs = this.olympic.getcountrylist().subscribe(data => {
      this.countryNB = data.length;
    })
    this.subscriptions.push(subs);
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
    let clicked = 0;
    if (e.active[0] !== undefined && e.active[0].index !== undefined) {
      clicked = e.active[0].index;
      if (clicked >= 0 && clicked < this.countryNB)
        this.changeview(clicked);
    }
  }

  public changeview(clicked: number) {
    this.olympic.getcountrylist().subscribe(data => {
      this.router.navigate(['/country', data[clicked]])
    })
  }

  ngOnDestroy() {
    this.subscriptions.forEach(data => data.unsubscribe());
  }
}
