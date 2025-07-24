import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectIsLoggedIn, selectUser } from '../store/auth.selectors';
import { map, take, switchMap } from 'rxjs/operators';
import { UserRoleType } from '../model/auth.model';
import { of } from 'rxjs';

export const roleGuard = (allowedRoles: UserRoleType[]): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const store = inject(Store);
    
    return store.select(selectIsLoggedIn).pipe(
      take(1),
      switchMap(isLoggedIn => {
        if (!isLoggedIn) {
          router.navigate(['/login']);
          return of(false);
        }
        
        return store.select(selectUser).pipe(
          take(1),
          map(user => {
            if (!user) {
              router.navigate(['/login']);
              return false;
            }
            
            if (allowedRoles.includes(user.role)) {
              return true;
            } else {
              router.navigate(['/dashboard']);
              return false;
            }
          })
        );
      })
    );
  };
};