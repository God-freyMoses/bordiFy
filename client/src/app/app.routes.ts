// src/app/app.routes.ts

import { Route } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { HrDashboardComponent } from './dashboards/hr-dashboard/hr-dashboard.component';
import { AuthGuard } from './auth/guards/auth.guard';

export const routes: Route[] = [
    {
        path: 'login',
        component: LoginComponent,
        title: 'Login'
    },
    {
        path: 'register',
        component: RegisterComponent,
        title: 'HR Registration'
    },
    {
        path: 'dashboard',
        component: HrDashboardComponent,
        title: 'Dashboard',
        canActivate: [AuthGuard]
    },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' }
];
