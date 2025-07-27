import {UserToken} from './user.model';

export interface LoginResponse {
  statusCode: number;
  success: boolean;
  message: string;
  data: UserToken;
}
