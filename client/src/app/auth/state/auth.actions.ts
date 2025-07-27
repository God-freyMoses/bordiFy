import { createAction, props } from '@ngrx/store';
import { UserToken } from '../../shared/models/user.model';
import {RegisterRequest} from '../../shared/models/register-request.model';
import {LoginRequest} from '../../shared/models/login-request.model';

export const login = createAction(
  '[Auth] Login',
  props<{ credentials: LoginRequest }>()
);

export const loginSuccess = createAction(
  '[Auth] Login Success',
  props<{ userToken: UserToken }>()
);

export const loginFailure = createAction(
  '[Auth] Login Failure',
  props<{ error: string }>()
);

export const registerHr = createAction(
  '[Auth] Register HR',
  props<{ registerData: RegisterRequest }>()
);

export const registerHrSuccess = createAction('[Auth] Register HR Success');
export const registerHrFailure = createAction(
  '[Auth] Register HR Failure',
  props<{ error: string }>()
);

export const logout = createAction('[Auth] Logout');
