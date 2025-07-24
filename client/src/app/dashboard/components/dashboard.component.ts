import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../share/components/navbar.component';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectUser } from '../../auth/store/auth.selectors';
import { TokenService } from '../../auth/services/token.service';

@Component({
  selector: 'dashboard',
  standalone: true,
  imports: [NavbarComponent, CommonModule, RouterLink],
  template: `
    <app-navbar></app-navbar>
    <div class="container mx-auto px-4 py-6">
      <h1 class="text-2xl font-bold mb-4">Welcome, {{ userName }}!</h1>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-6">
        <!-- Stats cards -->
        <div class="bg-white overflow-hidden shadow rounded-lg p-6">
          <dl>
            <dt class="text-sm font-medium text-gray-500 truncate">Total Templates</dt>
            <dd class="mt-1 text-3xl font-semibold text-gray-900">0</dd>
          </dl>
        </div>
        
        <div class="bg-white overflow-hidden shadow rounded-lg p-6">
          <dl>
            <dt class="text-sm font-medium text-gray-500 truncate">Active Onboardings</dt>
            <dd class="mt-1 text-3xl font-semibold text-gray-900">0</dd>
          </dl>
        </div>
        
        <div class="bg-white overflow-hidden shadow rounded-lg p-6">
          <dl>
            <dt class="text-sm font-medium text-gray-500 truncate">Completed Tasks</dt>
            <dd class="mt-1 text-3xl font-semibold text-gray-900">0</dd>
          </dl>
        </div>
      </div>
      
      <!-- Quick actions -->
      <div class="mt-8">
        <h2 class="text-lg font-medium text-gray-900 mb-4">Quick Actions</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <a routerLink="/templates" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-teal-600 hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
            View Templates
          </a>
          <a *ngIf="userRole === 'HR_MANAGER'" routerLink="/templates/create" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-teal-600 hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
            Create Template
          </a>
          <a *ngIf="userRole === 'HR_MANAGER'" routerLink="/register-hire" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-teal-600 hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
            Register New Hire
          </a>
          <a *ngIf="userRole === 'NEW_HIRE'" routerLink="/tasks" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-teal-600 hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
            My Tasks
          </a>
        </div>
      </div>
    </div>
  `,
})
export class DashboardComponent implements OnInit {
  userName = 'User';
  userRole = '';

  constructor(
    private store: Store,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    // Get user info from store
    this.store.select(selectUser).subscribe(user => {
      if (user) {
        this.userName = `${user.firstName} ${user.lastName}`;
        this.userRole = user.role;
      } else {
        // Fallback to token service if store is empty
        this.userRole = this.tokenService.getUserRole() || '';
      }
    });
  }
}
