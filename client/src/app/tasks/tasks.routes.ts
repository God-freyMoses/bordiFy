import { Routes } from '@angular/router';
import { authGuard } from '../auth/guards/auth.guard';
import { roleGuard } from '../auth/guards/role.guard';

export const TASKS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./components/task-list/task-list.component').then(m => m.TaskListComponent),
    canActivate: [authGuard, () => roleGuard(['NEW_HIRE'])]
  }
];