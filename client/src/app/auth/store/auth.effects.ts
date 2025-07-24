import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { AuthService } from '../service/auth.service';
import * as AuthActions from './auth.actions';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private router: Router
  ) {}

  // REGISTER HR EFFECT

  registerHr$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.REGISTER_HR),
      switchMap(({ credentials }) =>
        this.authService.registerHr(credentials).pipe(
          map((response) => {
            if (response.success) {
              return AuthActions.REGISTER_SUCCESS({ data: response.data });
            } else {
              return AuthActions.REGISTER_FAILURE({ error: response.message });
            }
          }),
          catchError((error) =>
            of(AuthActions.REGISTER_FAILURE({ error: error.message }))
          )
        )
      )
    )
  );
  
  // REGISTER HIRE EFFECT

  registerHire$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.REGISTER_HIRE),
      switchMap(({ credentials }) =>
        this.authService.registerHire(credentials).pipe(
          map((response) => {
            if (response.success) {
              return AuthActions.REGISTER_SUCCESS({ data: response.data });
            } else {
              return AuthActions.REGISTER_FAILURE({ error: response.message });
            }
          }),
          catchError((error) =>
            of(AuthActions.REGISTER_FAILURE({ error: error.message }))
          )
        )
      )
    )
  );
  
  // REGISTER SUCCESS EFFECT
  
  registerSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.REGISTER_SUCCESS),
      tap(() => {
        this.router.navigate(['/login']);
      })
    ),
    { dispatch: false }
  );

  // LOGIN EFFECT

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.LOGIN),
      switchMap(({ credentials }) =>
        this.authService.login(credentials).pipe(
          map((response) => {
            if (response.success) {
              return AuthActions.LOGIN_SUCCESS({ data: response.data });
            } else {
              return AuthActions.LOGIN_FAILURE({ error: response.message });
            }
          }),
          catchError((error) =>
            of(AuthActions.LOGIN_FAILURE({ error: error.message }))
          )
        )
      )
    )
  );
  
  // LOGIN SUCCESS EFFECT
  
  loginSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.LOGIN_SUCCESS),
      tap(() => {
        this.router.navigate(['/dashboard']);
      })
    ),
    { dispatch: false }
  );
  
  // LOGOUT EFFECT
  
  logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.LOGOUT),
      tap(() => {
        this.authService.logout();
        this.router.navigate(['/login']);
      })
    ),
    { dispatch: false }
  );
}