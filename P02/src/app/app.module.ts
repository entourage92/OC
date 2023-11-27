import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { OlympicComponent } from './components/olympic/olympic.component';
import { CountryComponent } from './components/country/country.component';
import {MatDividerModule} from '@angular/material/divider';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { NgChartsModule } from 'ng2-charts';
import { KeyValuePipe } from '@angular/common';

@NgModule({
  declarations: [AppComponent, HomeComponent, NotFoundComponent, OlympicComponent, CountryComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, NgChartsModule, MatDividerModule],
  providers: [KeyValuePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
