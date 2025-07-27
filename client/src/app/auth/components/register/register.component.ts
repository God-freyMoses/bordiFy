// src/app/auth/components/register/register.component.ts
import { Component, inject } from '@angular/core';
import { CommonModule, AsyncPipe } from '@angular/common'; // Add AsyncPipe
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import * as AuthActions from '../../state/auth.actions';
import { RegisterRequest } from '../../../shared/models/register-request.model';
import { selectAuthError } from '../../state/auth.selectors';

@Component({
  selector: 'app-register',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AsyncPipe
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private store = inject(Store);

  registerForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    companyName: ['', Validators.required]
  });

  errorMessage$ = this.store.select(selectAuthError);

  onSubmit() {
    if (this.registerForm.valid) {
      const registerData: RegisterRequest = this.registerForm.value as RegisterRequest;
      this.store.dispatch(AuthActions.registerHr({ registerData }));
    }
  }
}
