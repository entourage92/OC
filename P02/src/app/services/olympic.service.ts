import { HttpClient } from '@angular/common/http';
import { KeyValuePipe } from '@angular/common';
import { Injectable, Component } from '@angular/core';
import { BehaviorSubject, Observable, Subscription, pipe, distinct } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import * as JsonData from '../../assets/mock/olympic.json';
import { Countrytype } from '../interfaces/countrytype';
import { participation } from '../interfaces/participationtype';
import {Router} from "@angular/router";


@Injectable({
  providedIn: 'root',
})

export class OlympicService {
  private olympicUrl = './assets/mock/olympic.json';
  private olympics$ = new BehaviorSubject<Countrytype[]>([]);
  private dataset : Countrytype[] = []; 
  subscription: Subscription | undefined;

  constructor(private http: HttpClient,
   private keyValue: KeyValuePipe, private router: Router) {

    this.fillcountrype();
  }

  getdataset(): Observable<Countrytype[]> {
   return this.olympics$;
 }

 fillcountrype(){
  this.http.get<Countrytype[]>(this.olympicUrl).pipe(
    //  map(data => this.dataset = data),
    catchError((error, caught) => {
      this.router.navigate(['/404']);
      return caught;
    })
    ).subscribe(countrytype => this.olympics$.next(countrytype));
}

getcountrylist(): Observable<string[]>{
 return this.getdataset().pipe(
  map((data: Countrytype[]) => {
    let countrylist: string[] = [];
    data.forEach(item => countrylist.push(item.country));
    return (countrylist);
  } )
  )
}

getevents(): Observable<string[]>{
  let countrylist: string[] = [];
  return this.getdataset().pipe(
    map((data: Countrytype[]) => {
      data.forEach(item => {
        (item.participations.forEach(item => {
          if (!countrylist.includes(item.city))
            countrylist.push(item.city)
        }))
      });
      return countrylist
    })
    )
}

getcountrymedal(): Observable<number[]>{
 return this.getdataset().pipe(
  map((data: Countrytype[]) => {
    let countrymedal : number[] = [];
    let i: number = 0;
    for(let c of data){
      countrymedal[i] = 0;
      for  (let  event of c.participations){
        countrymedal[i] += event.medalsCount
      }
      i++
    }    
    return (countrymedal);
  })
  )
}
}
