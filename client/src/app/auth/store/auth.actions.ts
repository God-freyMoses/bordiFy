import {createAction, props} from '@ngrx/store';
import {LoginRequestType, RegisterRequestType, UserTokenType, UserType} from '../model/auth.model';



export let REGISTER = undefined;


// REGISTER ACTIONS

export const REGISTER_HR = createAction('[Auth] Register HR', props<{credentials: RegisterRequestType}>());
export const REGISTER_HIRE = createAction('[Auth] Register Hire', props<{credentials: RegisterRequestType}>());
export const REGISTER_SUCCESS = createAction('[Auth] Register success', props<{data: UserType}>());
export const REGISTER_FAILURE = createAction('[Auth] Register fail', props<{error: any}>());

// LOGIN ACTIONS

export const LOGIN = createAction('[Auth] Login', props<{credentials: LoginRequestType}>());
export const LOGIN_SUCCESS = createAction('[Auth] Login success', props<{data: UserTokenType}>());
export const LOGIN_FAILURE = createAction('[Auth] Login fail', props<{error: any}>());

// LOGOUT ACTIONS

export const LOGOUT = createAction('[Auth] Logout');
