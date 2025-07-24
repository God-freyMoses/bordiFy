
import {HttpHandlerFn, HttpRequest} from '@angular/common/http';
import {inject} from '@angular/core';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  // Get token from localStorage
  const token = localStorage.getItem('auth');
  let authToken = null;
  
  if (token) {
    try {
      const parsedToken = JSON.parse(token);
      if (parsedToken.auth && parsedToken.auth.token) {
        authToken = 'Bearer ' + parsedToken.auth.token;
      }
    } catch (e) {
      console.error('Error parsing auth token', e);
    }
  }
  
  // Only add auth header if we have a token
  if (authToken) {
    const newReq = req.clone({
      headers: req.headers.append('Authorization', authToken).set('Content-Type', 'application/json')
    });
    return next(newReq);
  }
  
  // Otherwise proceed with the original request
  return next(req);
}
