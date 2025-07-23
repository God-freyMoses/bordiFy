import {Component} from '@angular/core';
import { NavbarComponent } from '../../share/components/navbar.component';

@Component({
  selector: 'dashboard',
  imports: [NavbarComponent],
  template: `
    <app-navbar></app-navbar>
    <div class="container mx-auto px-4 py-6">
      <h1 class="text-2xl font-bold mb-4">Dashboard</h1>
      <p>Welcome to your Boardify dashboard!</p>
    </div>
  `,
})
export class DashboardComponent {}
