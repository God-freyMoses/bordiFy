// src/app/shared/models/register-request.model.ts

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  companyName: string;
  // Additional fields for Hire registration (not used in frontend)
  gender?: string;
  title?: string;
  pictureUrl?: string;
  departmentId?: string;
}
