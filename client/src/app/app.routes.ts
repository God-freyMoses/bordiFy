import { Routes } from '@angular/router';
import {AUTH_ROUTES} from './auth/auth.routes';
import {DASHBOARD_ROUTES} from './dashboard/dashboard.routes';
import {templatesRoutes} from './templates/templates.routes';
import {TASKS_ROUTES} from './tasks/tasks.routes';

export const routes: Routes = [
  ...AUTH_ROUTES,
  ...DASHBOARD_ROUTES,
  ...templatesRoutes,
  {path: 'tasks', children: TASKS_ROUTES},
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  {path: '404', loadComponent: () => import('./share/components/notfound.component').then(m => m.NotFoundComponent)},
  {path: '**', redirectTo: '404', pathMatch: 'full'},
];
