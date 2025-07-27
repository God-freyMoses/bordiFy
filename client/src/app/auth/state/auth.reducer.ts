// src/app/auth/state/auth.reducer.ts

import { createReducer, on } from '@ngrx/store';
import * as AuthActions from './auth.actions';
import { UserToken } from '../../shared/models/user.model';

export const authFeatureKey = 'auth';

export interface AuthState {
  user: UserToken | null;
  loading: boolean;
  error: string | null;
}

export const initialState: AuthState = {
  user: null,
  loading: false,
  error: null
};

export const authReducer = createReducer(
  initialState,
  on(AuthActions.login, state => ({ ...state, loading: true, error: null })),
  on(AuthActions.loginSuccess, (state, { userToken }) => ({
    ...state,
    user: userToken,
    loading: false
  })),
  on(AuthActions.loginFailure, (state, { error }) => ({
    ...state,
    error,
    loading: false
  })),
  on(AuthActions.registerHr, state => ({ ...state, loading: true, error: null })),
  on(AuthActions.registerHrSuccess, state => ({ ...state, loading: false })),
  on(AuthActions.registerHrFailure, (state, { error }) => ({
    ...state,
    error,
    loading: false
  })),
  on(AuthActions.logout, () => initialState)
);
