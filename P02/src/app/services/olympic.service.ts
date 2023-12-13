import {HttpClient} from '@angular/common/http';
import {KeyValuePipe} from '@angular/common';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subscription, pipe} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Countrytype} from '../interfaces/countrytype';
import {Router} from "@angular/router";


@Injectable({
  providedIn: 'root',
})

export class OlympicService {
  private olympicUrl = './assets/mock/olympic.json';
  private olympics$ = new BehaviorSubject<Countrytype[]>([]);
  private dataset: Countrytype[] = [];
  subscription: Subscription | undefined;

  constructor(private http: HttpClient,
              private keyValue: KeyValuePipe, private router: Router) {
    this.fillcountrype();
  }

  getdataset(): Observable<Countrytype[]> {
    return this.olympics$;
  }

  fillcountrype() {
    this.http.get<Countrytype[]>(this.olympicUrl).pipe(
      //  map(data => this.dataset = data),
      catchError((error, caught) => {
        this.router.navigate(['/404']);
        return caught;
      })
    ).subscribe(countrytype => this.olympics$.next(countrytype));
  }

  getcountrylist(): Observable<string[]> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let countrylist: string[] = [];
        data.forEach(item => countrylist.push(item.country));
        return (countrylist);
      })
    )
  }

  getevents(): Observable<Set<string>> {
    let countrylist: Set<string> = new Set();
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        data.forEach(item => {
          (item.participations.forEach(item => {
            countrylist.add(item.city)
          }))
        });
        return countrylist
      })
    )
  }

  getmedalpercountry(countryname: string): Observable<number> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let countrymedal: number = 0;
        for (let c of data) {
          if (c.country == countryname) {
            for (let event of c.participations) {
              countrymedal += event.medalsCount
            }
          }
        }
        return (countrymedal);
      })
    )
  }

  getcitypercountry(countryname: string): Observable<Set<string>> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let citylist: Set<string> = new Set();
        for (let c of data) {
          if (c.country == countryname) {
            for (let event of c.participations) {
              citylist.add(event.city)
            }
          }
        }
        return (citylist);
      })
    )
  }

  getmedalperevent(countryname: string): Observable<number[]> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let citylist: number[] = [];
        let i: number = 0;
        for (let c of data) {
          if (c.country == countryname) {
            for (let event of c.participations) {
              citylist[i] = event.medalsCount;
              i++;
            }
          }
        }
        return (citylist);
      })
    )
  }

  getathletepercountry(countryname: string): Observable<number> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let athlete: number = 0;
        for (let c of data) {
          if (c.country == countryname) {
            for (let event of c.participations) {
              athlete += event.medalsCount
            }
          }
        }
        return (athlete);
      })
    )
  }

  getcountrymedal(): Observable<number[]> {
    return this.getdataset().pipe(
      map((data: Countrytype[]) => {
        let countrymedal: number[] = [];
        let i: number = 0;
        for (let c of data) {
          countrymedal[i] = 0;
          for (let event of c.participations) {
            countrymedal[i] += event.medalsCount
          }
          i++
        }
        return (countrymedal);
      })
    )
  }
}
