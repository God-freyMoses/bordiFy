import { Routes } from '@angular/router';
import { TemplateListComponent } from './components/template-list/template-list.component';
import { TemplateDetailComponent } from './components/template-detail/template-detail.component';
import { authGuard } from '../auth/guards/auth.guard';
import { roleGuard } from '../auth/guards/role.guard';

export const templatesRoutes: Routes = [
  {
    path: 'templates',
    canActivate: [authGuard],
    children: [
      {
        path: '',
        component: TemplateListComponent
      },
      {
        path: 'create',
        component: TemplateDetailComponent,
        canActivate: [roleGuard(['HR_MANAGER'])]
      },
      {
        path: ':id',
        component: TemplateDetailComponent
      }
    ]
  }
];