import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../share/components/navbar.component';
import { Store } from '@ngrx/store';
import { selectUser } from '../../../auth/store/auth.selectors';
import { TokenService } from '../../../auth/service/token.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

interface TaskType {
  id: string;
  title: string;
  description: string;
  dueDate: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
  requiresSignature: boolean;
}

interface ProgressType {
  id: number;
  taskId: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
  completedAt: string | null;
  signedAt: string | null;
  signatureData: string | null;
  createdAt: string;
  task: TaskType;
}

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  template: `
    <app-navbar></app-navbar>
    
    <div class="container mx-auto px-4 py-6">
      <h1 class="text-2xl font-bold mb-6">My Tasks</h1>
      
      <div *ngIf="loading" class="flex justify-center">
        <div class="animate-spin rounded-full h-10 w-10 border-b-2 border-teal-500"></div>
      </div>
      
      <div *ngIf="error" class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
        {{ error }}
      </div>
      
      <div *ngIf="!loading && progressItems.length === 0" class="bg-blue-100 border border-blue-400 text-blue-700 px-4 py-3 rounded mb-4">
        You don't have any tasks assigned yet.
      </div>
      
      <div *ngIf="!loading && progressItems.length > 0" class="grid grid-cols-1 gap-4">
        <div *ngFor="let progress of progressItems" class="bg-white shadow-md rounded-lg p-6 border-l-4"
             [ngClass]="{
               'border-yellow-500': progress.status === 'PENDING',
               'border-blue-500': progress.status === 'IN_PROGRESS',
               'border-green-500': progress.status === 'COMPLETED'
             }">
          <div class="flex justify-between items-start">
            <div>
              <h2 class="text-xl font-semibold mb-2">{{ progress.task.title }}</h2>
              <p class="text-gray-600 mb-4">{{ progress.task.description }}</p>
              
              <div class="flex items-center mb-2">
                <span class="text-sm font-medium text-gray-500 mr-2">Status:</span>
                <span class="px-2 py-1 text-xs rounded-full"
                      [ngClass]="{
                        'bg-yellow-100 text-yellow-800': progress.status === 'PENDING',
                        'bg-blue-100 text-blue-800': progress.status === 'IN_PROGRESS',
                        'bg-green-100 text-green-800': progress.status === 'COMPLETED'
                      }">
                  {{ progress.status }}
                </span>
              </div>
              
              <div *ngIf="progress.task.dueDate" class="flex items-center mb-2">
                <span class="text-sm font-medium text-gray-500 mr-2">Due Date:</span>
                <span class="text-sm">{{ progress.task.dueDate | date:'mediumDate' }}</span>
              </div>
            </div>
            
            <div class="flex">
              <button *ngIf="progress.status === 'PENDING'" 
                      (click)="startTask(progress.id)"
                      class="px-3 py-1 bg-blue-500 text-white rounded-md hover:bg-blue-600 mr-2">
                Start
              </button>
              
              <button *ngIf="progress.status === 'IN_PROGRESS'" 
                      (click)="completeTask(progress.id)"
                      class="px-3 py-1 bg-green-500 text-white rounded-md hover:bg-green-600 mr-2">
                Complete
              </button>
              
              <button *ngIf="progress.task.requiresSignature && progress.status === 'COMPLETED' && !progress.signatureData" 
                      (click)="signTask(progress.id)"
                      class="px-3 py-1 bg-purple-500 text-white rounded-md hover:bg-purple-600">
                Sign
              </button>
            </div>
          </div>
          
          <div *ngIf="progress.completedAt" class="mt-4 text-sm text-gray-500">
            Completed on: {{ progress.completedAt | date:'medium' }}
          </div>
          
          <div *ngIf="progress.signedAt" class="mt-2 text-sm text-gray-500">
            Signed on: {{ progress.signedAt | date:'medium' }}
          </div>
        </div>
      </div>
    </div>
  `
})
export class TaskListComponent implements OnInit {
  progressItems: ProgressType[] = [];
  loading = false;
  error = '';
  userId = '';
  
  constructor(
    private http: HttpClient,
    private store: Store,
    private tokenService: TokenService
  ) {}
  
  ngOnInit(): void {
    // Get user info from store
    this.store.select(selectUser).subscribe(user => {
      if (user) {
        this.userId = user.id;
        this.loadTasks();
      }
    });
  }
  
  loadTasks(): void {
    this.loading = true;
    this.error = '';
    
    this.http.get<ProgressType[]>(`${environment.apiUrl}/api/progress/hire/${this.userId}`)
      .subscribe({
        next: (data) => {
          this.progressItems = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load tasks. Please try again.';
          this.loading = false;
          console.error('Error loading tasks:', err);
        }
      });
  }
  
  startTask(progressId: number): void {
    this.http.put(`${environment.apiUrl}/api/progress/${progressId}/start`, {})
      .subscribe({
        next: () => {
          this.loadTasks();
        },
        error: (err) => {
          this.error = 'Failed to start task. Please try again.';
          console.error('Error starting task:', err);
        }
      });
  }
  
  completeTask(progressId: number): void {
    this.http.put(`${environment.apiUrl}/api/progress/${progressId}/complete`, {})
      .subscribe({
        next: () => {
          this.loadTasks();
        },
        error: (err) => {
          this.error = 'Failed to complete task. Please try again.';
          console.error('Error completing task:', err);
        }
      });
  }
  
  signTask(progressId: number): void {
    // In a real app, this would open a signature pad
    const signatureData = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==';
    
    this.http.put(`${environment.apiUrl}/api/progress/${progressId}/sign`, { signatureData })
      .subscribe({
        next: () => {
          this.loadTasks();
        },
        error: (err) => {
          this.error = 'Failed to sign task. Please try again.';
          console.error('Error signing task:', err);
        }
      });
  }
}