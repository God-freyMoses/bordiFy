// src/app/shared/models/user.model.ts

export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
  createdAt: string;
  userType: string; // 'HR' or 'HIRE'
}

export interface UserToken {
  token: string;
  user: User;
}
