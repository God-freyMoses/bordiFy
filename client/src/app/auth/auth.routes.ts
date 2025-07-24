import {Route} from '@angular/router';
import {authGuard} from './guards/auth.guard';
import {roleGuard} from './guards/role.guard';

// LAZY LOAD COMPONENTS

export const AUTH_ROUTES: Route[] = [
  {path: 'login', loadComponent: () => import('./components/login.component').then(m => m.LoginComponent)},
  {path: 'register', loadComponent: () => import('./components/register.component').then(m => m.RegisterComponent)},
  {
    path: 'register-hire', 
    loadComponent: () => import('./components/register-hire.component').then(m => m.RegisterHireComponent),
    canActivate: [authGuard, () => roleGuard(['HR'])]
  },
];
