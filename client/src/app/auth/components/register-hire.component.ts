import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {Store} from '@ngrx/store';
import {REGISTER_HIRE} from '../store/auth.actions';
import {selectError, selectIsLoggedIn, selectLoading} from '../store/auth.selectors';
import {Observable, Subject, takeUntil} from 'rxjs';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'auth-register-hire',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  template: `
    <div class="w-screen h-screen grid place-items-center p-4 select-none">
      <div class="bg-white rounded-3xl shadow-md p-10 w-full max-w-md border-t-4 border-teal-500 mt-[-2rem] fade-in-up">
        <div class="flex flex-col items-center">

          <div class="mb-6">
            <svg xmlns="http://www.w3.org/2000/svg" width="46" height="46" fill="currentColor" class="bi bi-person-plus" viewBox="0 0 16 16">
              <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z"/>
              <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5"/>
            </svg>
          </div>


          <!-- Welcome Text -->
          <h2 class="text-2xl font-semibold text-gray-800 mb-1">Register New Hire</h2>
          <p class="text-sm text-gray-500 mb-6">Create an account for a new employee</p>
        </div>

        <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="space-y-5">
          <!-- First Name -->
          <div>
            <label class="block text-gray-700 mb-1" for="firstName">First Name</label>
            <input id="firstName" type="text" formControlName="firstName" placeholder="First Name"
                   class="w-full px-4 py-2 placeholder-gray-400 border border-gray-300 rounded-md focus:ring-2 focus:ring-teal-500 focus:outline-none" />
          </div>

          <!-- Last Name -->
          <div>
            <label class="block text-gray-700 mb-1" for="lastName">Last Name</label>
            <input id="lastName" type="text" formControlName="lastName" placeholder="Last Name"
                   class="w-full px-4 py-2 placeholder-gray-400 border border-gray-300 rounded-md focus:ring-2 focus:ring-teal-500 focus:outline-none" />
          </div>

          <!-- Email -->
          <div>
            <label class="block text-gray-700 mb-1" for="email">Email</label>
            <input id="email" type="email" formControlName="email" placeholder="Email"
                   class="w-full px-4 py-2 placeholder-gray-400 border border-gray-300 rounded-md focus:ring-2 focus:ring-teal-500 focus:outline-none" />
          </div>

          <!-- Password -->
          <div>
            <label class="block text-gray-700 mb-1" for="password">Password</label>
            <div class="relative">
              <input [type]="showPassword ? 'text' : 'password'" id="password" formControlName="password" placeholder="Password"
                     class="w-full px-4 py-2 placeholder-gray-400 border border-gray-300 rounded-md focus:ring-2 focus:ring-teal-500 focus:outline-none pr-10" />
              <span class="absolute right-3 top-2.5 text-gray-400 cursor-pointer" (click)="togglePasswordVisibility()">
                <svg *ngIf="!showPassword" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-fill mt-1" viewBox="0 0 16 16">
                  <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0"/>
                  <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8m8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7"/>
                </svg>
                <svg *ngIf="showPassword" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash-fill mt-1" viewBox="0 0 16 16">
                  <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7 7 0 0 0 2.79-.588M5.21 3.088A7 7 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474z"/>
                  <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829z"/>
                  <path fill-rule="evenodd" d="M13.646 14.354l-12-12 .708-.708 12 12z"/>
                </svg>
              </span>
            </div>
          </div>

          <!-- Department -->
          <div>
            <label class="block text-gray-700 mb-1" for="department">Department</label>
            <input id="department" type="text" formControlName="department" placeholder="Department"
                   class="w-full px-4 py-2 placeholder-gray-400 border border-gray-300 rounded-md focus:ring-2 focus:ring-teal-500 focus:outline-none" />
          </div>

          <!-- Error message -->
          <div *ngIf="error$ | async as error" class="text-red-500 text-sm mb-2">
            {{ error }}
          </div>

          <!-- Register Button -->
          <button type="submit" [disabled]="registerForm.invalid || (loading$ | async)"
                  class="w-full bg-teal-500 hover:bg-teal-600 text-white py-2 rounded-md transition-colors cursor-pointer disabled:bg-gray-400 disabled:cursor-not-allowed">
            <span *ngIf="loading$ | async">Loading...</span>
            <span *ngIf="!(loading$ | async)">Register New Hire</span>
          </button>

          <!-- Back to Dashboard -->
          <div class="flex items-center justify-center text-sm">
            <a routerLink="/dashboard" class="text-teal-500 hover:underline">Back to Dashboard</a>
          </div>
        </form>
      </div>
    </div>
  `,
})
export class RegisterHireComponent implements OnInit, OnDestroy {
  registerForm!: FormGroup;
  showPassword = false;
  private destroy$ = new Subject<void>();

  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  isLoggedIn$: Observable<boolean>;


  constructor(
    private fb: FormBuilder,
    private store: Store,
    private router: Router
  ) {
    this.loading$ = this.store.select(selectLoading);
    this.error$ = this.store.select(selectError);
    this.isLoggedIn$ = this.store.select(selectIsLoggedIn);
  }

  ngOnInit(): void {
    this.initForm();

    // Redirect if not logged in
    this.isLoggedIn$
      .pipe(takeUntil(this.destroy$))
      .subscribe(isLoggedIn => {
        if (!isLoggedIn) {
          this.router.navigate(['/login']);
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  initForm(): void {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      department: ['', [Validators.required]],
      role: ['HIRE']
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.store.dispatch(REGISTER_HIRE({
        credentials: this.registerForm.value
      }));
    }
  }
}
