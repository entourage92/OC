import {Routes} from '@angular/router';
import {UserComponent} from "./user/user.component";
import {AdminComponent} from "./admin/admin.component";

export const routes: Routes = [
  {path: 'admin', component: AdminComponent},
  {path: 'user', component: UserComponent},
];
