import { DynamicScriptLoaderService } from './service/scripts/dynamic-script-loader.service';
import { AppointmentsListComponent } from './components/appointments-list/appointments-list.component';
import { CustomersListComponent } from './components/customers-list/customers-list.component';
import { HttpClientModule } from "@angular/common/http";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ChartsModule } from "ng2-charts";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FileUploadModule } from "ng2-file-upload";
import { AgmCoreModule } from "@agm/core";

AgmCoreModule

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { NavigationComponent } from "./navigation/navigation.component";
import { FooterComponent } from "./footer/footer.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { LoginComponent } from "./login/login.component";
import { InjuriesUploadComponent } from './upload/injuries-upload/injuries-upload.component';
import { ClinicsUploadComponent } from './upload/clinics-upload/clinics-upload.component';
import { AppointmentsUploadComponent } from './upload/appointments-upload/appointments-upload.component';
import { CustomersUploadComponent } from './upload/customers-upload/customers-upload.component';
import { LoadMainComponent } from './upload/load-main/load-main.component';
import { CustomersStatisticsComponent } from './statistics/customers/customers-statistics/customers-statistics.component';
import { SingleAppointmentComponent } from './components/single-appointment/single-appointment.component';
import { SingleCustomerComponent } from './components/single-customer/single-customer.component';
import { AdvCustomerAppointmentsComponent } from './components/adv-customer-appointments/adv-customer-appointments.component';
import { CustomersAgeDistributionComponent } from './components/customers-age-distribution/customers-age-distribution.component';
import { PieChartComponent } from './graphs/pie-chart/pie-chart.component';
import { ReferralInfluenceComponent } from './components/referral-influence/referral-influence.component';
import { RelativesInjuryGraphComponent } from './components/relatives-injury-graph/relatives-injury-graph.component';
import { MarketingPageComponent } from './statistics/marketing/marketing-page/marketing-page.component';
import { BarChartComponent } from './graphs/bar-chart/bar-chart.component';
import { FillingRatesComponent } from './components/filling-rates/filling-rates.component';
import { LineGraphComponent } from './graphs/line-graph/line-graph.component';
import { InjuriesStatisticsComponent } from './statistics/injuries/injuries-statistics/injuries-statistics.component';
import { InjuriesRecoveryTimeComponent } from './components/injuries-recovery-time/injuries-recovery-time.component';
import { InjuryAgeRecoveryComponent } from './components/injury-age-recovery/injury-age-recovery.component';
import { RadarGraphComponent } from './graphs/radar-graph/radar-graph.component';
import { EventsPageComponent } from './statistics/marketing/events-page/events-page.component';
import { SingleEventComponent } from './components/single-event/single-event.component';
import { EventFormComponent } from './components/event-form/event-form.component';
import { HelpPageComponent } from './components/help-page/help-page.component';
import { WeatherPageComponent } from './statistics/marketing/weather-page/weather-page.component';
import { WeatherCvsLoadComponent } from './components/weather-cvs-load/weather-cvs-load.component';
import { WeatherYearGraphComponent } from './components/weather-year-graph/weather-year-graph.component';
import { EventsManageComponent } from './statistics/events/events-manage/events-manage.component';
import { EventsListComponent } from './components/events-list/events-list.component';
import { CustomerFormComponent } from './components/customer-form/customer-form.component';
import { AppointmentFormComponent } from './components/appointment-form/appointment-form.component';
import { CustomersManageComponent } from './statistics/customers/customers-manage/customers-manage.component';
import { AppointmentsManageComponent } from './statistics/appointments/appointments-manage/appointments-manage.component';
import { SpinnerLoadComponent } from './ui/spinner-load/spinner-load.component';
import { BubbleChartComponent } from './graphs/bubble-chart/bubble-chart.component';
import { WeatherSlopeGraphComponent } from './components/weather-slope-graph/weather-slope-graph.component';
import { ClinicFormComponent } from './components/clinic-form/clinic-form.component';
import { EventsUploadComponent } from './upload/events-upload/events-upload.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    FooterComponent,
    SidebarComponent,
    CustomersListComponent,
    LoginComponent,
    InjuriesUploadComponent,
    ClinicsUploadComponent,
    AppointmentsUploadComponent,
    CustomersUploadComponent,
    AppointmentsListComponent,
    LoadMainComponent,
    CustomersStatisticsComponent,
    SingleAppointmentComponent,
    SingleCustomerComponent,
    AdvCustomerAppointmentsComponent,
    CustomersAgeDistributionComponent,
    PieChartComponent,
    ReferralInfluenceComponent,
    RelativesInjuryGraphComponent,
    MarketingPageComponent,
    BarChartComponent,
    FillingRatesComponent,
    LineGraphComponent,
    InjuriesStatisticsComponent,
    InjuriesRecoveryTimeComponent,
    InjuryAgeRecoveryComponent,
    RadarGraphComponent,
    EventsPageComponent,
    SingleEventComponent,
    EventFormComponent,
    HelpPageComponent,
    WeatherPageComponent,
    WeatherCvsLoadComponent,
    WeatherYearGraphComponent,
    EventsManageComponent,
    EventsListComponent,
    CustomerFormComponent,
    AppointmentFormComponent,
    CustomersManageComponent,
    AppointmentsManageComponent,
    SpinnerLoadComponent,
    BubbleChartComponent,
    WeatherSlopeGraphComponent,
    ClinicFormComponent,
    EventsUploadComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FileUploadModule,
    ChartsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBiPhB64OBDZkkE5BxyqLFBpC_cFs43Uuw'
    })
  ],
  providers: [DynamicScriptLoaderService],
  bootstrap: [AppComponent]
})
export class AppModule {}
