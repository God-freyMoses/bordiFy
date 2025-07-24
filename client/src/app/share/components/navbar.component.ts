import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { selectUser } from '../../auth/store/auth.selectors';
import { LOGOUT } from '../../auth/store/auth.actions';
import { TokenService } from '../../auth/services/token.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
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
            <a *ngIf="userRole === 'HR_MANAGER'" routerLink="/register-hire" routerLinkActive="text-teal-600 font-medium"
               class="text-gray-600 hover:text-teal-600 transition-colors">Register Hire</a>
            <a *ngIf="userRole === 'NEW_HIRE'" routerLink="/tasks" routerLinkActive="text-teal-600 font-medium"
               class="text-gray-600 hover:text-teal-600 transition-colors">My Tasks</a>
          </div>
          <div class="flex items-center space-x-4">
            <div class="text-sm text-gray-700">{{ userName }}</div>
            <div class="h-8 w-8 rounded-full bg-teal-500 flex items-center justify-center text-white font-medium">
              {{ userInitials }}
            </div>
            <button (click)="logout()" class="text-gray-600 hover:text-teal-600 transition-colors">Logout</button>
          </div>
        </div>
      </div>
    </nav>
  `
})
export class NavbarComponent implements OnInit {
  userName = 'User';
  userInitials = 'U';
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
        this.userInitials = `${user.firstName.charAt(0)}${user.lastName.charAt(0)}`;
        this.userRole = user.role;
      } else {
        // Fallback to token service if store is empty
        this.userRole = this.tokenService.getUserRole() || '';
      }
    });
  }
  
  logout(): void {
    this.store.dispatch(LOGOUT());
  }
}