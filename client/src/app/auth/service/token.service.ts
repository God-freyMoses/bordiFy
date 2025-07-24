import { Injectable } from '@angular/core';
import { UserRoleType } from '../model/auth.model';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'auth';

  /**
   * Get the JWT token from local storage
   */
  getToken(): string | null {
    const authData = localStorage.getItem(this.TOKEN_KEY);
    if (!authData) return null;
    
    try {
      const parsedData = JSON.parse(authData);
      return parsedData.auth?.token || null;
    } catch (e) {
      console.error('Error parsing auth token', e);
      return null;
    }
  }

  /**
   * Get the user role from the token
   */
  getUserRole(): UserRoleType | null {
    const authData = localStorage.getItem(this.TOKEN_KEY);
    if (!authData) return null;
    
    try {
      const parsedData = JSON.parse(authData);
      return parsedData.auth?.user?.role || null;
    } catch (e) {
      console.error('Error parsing auth data', e);
      return null;
    }
  }

  /**
   * Check if the user has a specific role
   */
  hasRole(role: UserRoleType): boolean {
    const userRole = this.getUserRole();
    return userRole === role;
  }

  /**
   * Check if the user has any of the specified roles
   */
  hasAnyRole(roles: UserRoleType[]): boolean {
    const userRole = this.getUserRole();
    return userRole ? roles.includes(userRole) : false;
  }

  /**
   * Check if the user is authenticated
   */
  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  /**
   * Clear the authentication token
   */
  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}