import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { OlympicComponent } from './components/olympic/olympic.component';
import { CountryComponent } from './components/country/country.component';

const routes: Routes = [
{
  path: '',
  component: HomeComponent,
},
{
  path: 'olympic',
  component: OlympicComponent,
  title: 'olympic'
},
{
  path: 'country/:countryname',
  component: CountryComponent,
},
{
    path: '**', // wildcard
    component: NotFoundComponent,
  },

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
