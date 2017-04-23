import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule} from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

// used to create fake backend
import { fakeBackendProvider } from './helpers/index';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';

// other imports

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/index';
import { UserTableComponent } from './components/user-table/user-table.component';
import { UserDataItemComponent } from './components/user-data-item/user-data-item.component';
import { AlertComponent } from './components/alert/index';
import { AuthGuard } from './services/index';
import { AlertService, AuthenticationService, UserService } from './services/index';
import { HomeComponent } from './components/home/index';
import { LoginComponent } from './components/login/index';
import { RegisterComponent } from './components/register/index';
import { UserReportFeedComponent } from './components/user-report-feed/index';
import {ReportService} from "./services/report/report.service";

const appRoutes: Routes = [
  { path: 'user-table', component: UserTableComponent, canActivate: [AuthGuard]},
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'feed', component: UserReportFeedComponent, canActivate: [AuthGuard] },
  // otherwise redirect to home
  { path: '*', redirectTo: '' }

];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserTableComponent,
    UserDataItemComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserReportFeedComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    UserService,
    ReportService,

    // providers used to create fake backend
    fakeBackendProvider,
    MockBackend,
    BaseRequestOptions
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
