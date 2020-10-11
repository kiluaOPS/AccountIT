import { ClinicFormComponent } from './components/clinic-form/clinic-form.component';
import { AppointmentFormComponent } from './components/appointment-form/appointment-form.component';
import { CustomerFormComponent } from './components/customer-form/customer-form.component';
import { AppointmentsManageComponent } from './statistics/appointments/appointments-manage/appointments-manage.component';
import { CustomersManageComponent } from './statistics/customers/customers-manage/customers-manage.component';
import { EventsManageComponent } from './statistics/events/events-manage/events-manage.component';
import { HelpPageComponent } from './components/help-page/help-page.component';
import { EventFormComponent } from './components/event-form/event-form.component';
import { EventsPageComponent } from './statistics/marketing/events-page/events-page.component';
import { InjuriesStatisticsComponent } from './statistics/injuries/injuries-statistics/injuries-statistics.component';
import { MarketingPageComponent } from './statistics/marketing/marketing-page/marketing-page.component';
import { AppointmentsListComponent } from './components/appointments-list/appointments-list.component';
import { CustomersListComponent } from './components/customers-list/customers-list.component';
import { SingleCustomerComponent } from './components/single-customer/single-customer.component';
import { SingleAppointmentComponent } from './components/single-appointment/single-appointment.component';
import { LoadMainComponent } from './upload/load-main/load-main.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CustomersStatisticsComponent } from './statistics/customers/customers-statistics/customers-statistics.component';
import { WeatherPageComponent } from './statistics/marketing/weather-page/weather-page.component';


const routes: Routes = [
  {path : '', component : LoginComponent},
  {path : 'login', component: LoginComponent},
  {path : 'upload', component : LoadMainComponent, canActivate : [AuthGuard]},
  {path : 'customers', component : CustomersManageComponent, canActivate : [AuthGuard]},
  {path : 'customer/:id', component : SingleCustomerComponent, canActivate : [AuthGuard]},
  {path : 'create-customer', component : CustomerFormComponent, canActivate : [AuthGuard]}, 
  {path : 'create-appointment', component : AppointmentFormComponent, canActivate : [AuthGuard]},
  {path : 'appointments', component : AppointmentsManageComponent, canActivate : [AuthGuard]},
  {path : 'appointment/:id', component : SingleAppointmentComponent, canActivate : [AuthGuard]},
  {path : 'events', component : EventsManageComponent, canActivate : [AuthGuard]},
  {path : 'statistics/customers', component : CustomersStatisticsComponent, canActivate : [AuthGuard]},
  {path : 'marketing/customers', component : MarketingPageComponent, canActivate : [AuthGuard]},
  {path : 'statistics/injuries', component : InjuriesStatisticsComponent, canActivate : [AuthGuard]},
  {path : 'marketing/events/:id', component : EventsPageComponent, canActivate : [AuthGuard]},
  {path : 'marketing/weather', component : WeatherPageComponent, canActivate : [AuthGuard]},
  {path : 'settings', component : ClinicFormComponent, canActivate : [AuthGuard]},
  {path : 'help', component : HelpPageComponent, canActivate : [AuthGuard]},
];




@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
