
import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of, tap } from 'rxjs';
import * as AuthActions from './auth.actions';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

export const authEffects = {
  login: createEffect(
    (actions$ = inject(Actions), authService = inject(AuthService)) => {
      return actions$.pipe(
        ofType(AuthActions.login),
        mergeMap(({credentials}) =>
          authService.login(credentials).pipe(
            map(userToken => AuthActions.loginSuccess({ userToken })),
            catchError(error => of(AuthActions.loginFailure({ error: error.message })))
          )
        )
      );
    },
    { functional: true }
  ),

  loginSuccess: createEffect(
    (actions$ = inject(Actions), router = inject(Router)) => {
      return actions$.pipe(
        ofType(AuthActions.loginSuccess),
        tap(() => router.navigate(['/dashboard']))
      );
    },
    { functional: true, dispatch: false }
  ),

  registerHr: createEffect(
    (actions$ = inject(Actions), authService = inject(AuthService)) => {
      return actions$.pipe(
        ofType(AuthActions.registerHr),
        mergeMap(({registerData}) =>
          authService.registerHr(registerData).pipe(
            map(() => AuthActions.registerHrSuccess()),
            catchError(error => of(AuthActions.registerHrFailure({ error: error.message })))
          )
        )
      );
    },
    { functional: true }
  ),

  registerSuccess: createEffect(
    (actions$ = inject(Actions), router = inject(Router)) => {
      return actions$.pipe(
        ofType(AuthActions.registerHrSuccess),
        tap(() => router.navigate(['/login']))
      );
    },
    { functional: true, dispatch: false }
  )
};
