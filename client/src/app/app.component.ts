import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TemplatesModule } from './templates/templates.module';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TemplatesModule],
  template: `
    <router-outlet />
  `
})
export class AppComponent {}
