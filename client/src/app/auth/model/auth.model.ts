export type UserRoleType = 'ADMIN' | 'USER' | 'HR' | 'HIRE';

export type LoginRequestType = {
  email: string;
  password: string;
};

export type RegisterRequestType = LoginRequestType & {
  role: UserRoleType;
};

export type UserType = {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRoleType;
  userType: string;
  createdAt: string;
}

export type UserTokenType = {
  token: string;
  user: UserType;
};


export type AuthStateType = {
  user: UserType | null;
  token: string | null;
  loading: boolean;
  isLoggedIn: boolean;
  error: string | null;
}
