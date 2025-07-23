import { Routes } from '@angular/router';
import { TemplateListComponent } from './components/template-list/template-list.component';
import { TemplateDetailComponent } from './components/template-detail/template-detail.component';

export const templatesRoutes: Routes = [
  {
    path: 'templates',
    children: [
      {
        path: '',
        component: TemplateListComponent
      },
      {
        path: 'create',
        component: TemplateDetailComponent
      },
      {
        path: ':id',
        component: TemplateDetailComponent
      }
    ]
  }
];