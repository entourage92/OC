import { Injectable } from '@angular/core';
import { CommonModule } from '@angular/common';

import * as JsonData from '../../assets/mock/olympic.json';
import { Countrytype } from '../interfaces/countrytype';
import { participation } from '../interfaces/participationtype';

@Injectable({
  providedIn: 'root'
})
export class ReaddataService {

  constructor() { }

  getAlldata(): Countrytype[]{
      let CountryDatas : Countrytype[] = [];

   for(const country in JsonData) {
    CountryDatas.push(JsonData[country]);
  }
  return (CountryDatas);
}
}
