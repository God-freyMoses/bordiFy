import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <nav class="bg-white shadow-md">
      <div class="container mx-auto px-4">
        <div class="flex justify-between items-center py-3">
          <div class="flex items-center">
            <a routerLink="/dashboard" class="text-xl font-bold text-teal-600">Boardify</a>
          </div>
          <div class="flex space-x-6">
            <a routerLink="/dashboard" routerLinkActive="text-teal-600 font-medium" 
               class="text-gray-600 hover:text-teal-600 transition-colors">Dashboard</a>
            <a routerLink="/templates" routerLinkActive="text-teal-600 font-medium" 
               class="text-gray-600 hover:text-teal-600 transition-colors">Templates</a>
          </div>
          <div>
            <a routerLink="/login" class="text-gray-600 hover:text-teal-600 transition-colors">Logout</a>
          </div>
        </div>
      </div>
    </nav>
  `
})
export class NavbarComponent {}