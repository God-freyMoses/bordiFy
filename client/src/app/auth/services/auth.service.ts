// src/app/auth/services/auth.service.ts
import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { environment } from '../../../enviroments/environment';
import { Observable, tap } from 'rxjs';
import { map } from 'rxjs/operators';
import { LoginRequest } from '../../shared/models/login-request.model';
import { RegisterRequest } from '../../shared/models/register-request.model';
import { UserToken } from '../../shared/models/user.model';
import * as AuthActions from '../state/auth.actions';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = environment.apiUrl;
  private readonly http = inject(HttpClient);
  private readonly store = inject(Store);

  login(loginRequest: LoginRequest): Observable<UserToken> {
    return this.http.post<{ data: UserToken }>(
      `${this.apiUrl}/api/users/login`,
      loginRequest
    ).pipe(
      map(response => response.data),
      tap(userToken => {
        // First update NgRx state
        this.store.dispatch(AuthActions.loginSuccess({ userToken }));

        // Then store token in localStorage
        this.storeToken(userToken.token);
      })
    );
  }

  registerHr(registerRequest: RegisterRequest): Observable<unknown> {
    return this.http.post(
      `${this.apiUrl}/api/users/register/hr`,
      registerRequest
    );
  }

  private storeToken(token: string): void {
    if (!token) {
      console.warn('Attempted to store empty token');
      return;
    }

    try {
      localStorage.setItem('auth_token', token);
      console.log('Token stored successfully');
    } catch (e) {
      console.error('LocalStorage setItem failed:', e);
      sessionStorage.setItem('auth_token', token);
    }
  }

  getToken(): string | null {
    try {
      return localStorage.getItem('auth_token') || sessionStorage.getItem('auth_token');
    } catch (e) {
      console.error('LocalStorage access error:', e);
      return null;
    }
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    sessionStorage.removeItem('auth_token');
    this.store.dispatch(AuthActions.logout());
  }
}
