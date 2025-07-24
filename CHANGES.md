# Changes Made to the Onboarding Application

## Authentication and Role-Based Access Control

### 1. Fixed UserMapper.java
- Fixed a bug in `UserMapper.java` where it was using `UserRole.HIRE` which doesn't exist in the `UserRole` enum.
- Changed it to use `UserRole.NEW_HIRE` to match the enum values.

### 2. Fixed Angular NgRx Store Issues
- Fixed the auth.reducer.ts file to handle `REGISTER_HR` and `REGISTER_HIRE` actions instead of the generic `REGISTER` action.
- Removed the unused `REGISTER` action from auth.actions.ts.

### 3. Fixed Role Name Mismatches
- Updated the UserRoleType in auth.model.ts to use 'HR_MANAGER' and 'NEW_HIRE' instead of 'HR' and 'HIRE'.
- Updated the role values in register.component.ts and register-hire.component.ts.
- Updated the role guards in auth.routes.ts and templates.routes.ts.

### 4. Fixed Role-Based Access Control in TemplateController
- Updated all @PreAuthorize annotations in TemplateController.java to use 'HR_MANAGER' and 'NEW_HIRE' instead of 'HR' and 'HIRE'.

## Security and Communication

- Verified that CORS is properly configured in SecurityConfig.java.
- Verified that JWT authentication is properly implemented.
- Verified that the frontend interceptor correctly adds the JWT token to requests.

## API Documentation

- Verified that Swagger UI is properly configured in OpenApiConfig.java.
- Verified that API endpoints have proper role-based access control.

## HR Manager Features

### 1. Fixed Role-Based Access Control in Dashboard
- Updated the dashboard.component.ts file to use 'HR_MANAGER' instead of 'HR' for role checks.
- Only HR Managers can now see the "Create Template" and "Register New Hire" buttons.

### 2. Fixed Role-Based Access Control in Template List
- Added userRole property to template-list.component.ts.
- Updated the template-list.component.html file to only show "Create Template" and "Delete" buttons to HR Managers.

### 3. Fixed Role-Based Access Control in Template Detail
- Added userRole property to template-detail.component.ts.
- Updated the template-detail.component.html file to only show "Edit" button to HR Managers.
- Added redirect logic to prevent non-HR Managers from accessing template creation/editing pages.

## New Hire Features

### 1. Added Task List Component for New Hires
- Created a new TaskListComponent to display tasks assigned to New Hires.
- Implemented UI for viewing task details, status, and due dates.
- Added functionality to start, complete, and sign tasks.

### 2. Added Backend Support for Task Management
- Created ProgressController to handle task-related operations.
- Implemented endpoints for retrieving, updating, and signing tasks.
- Added proper role-based access control to ensure New Hires can only access their own tasks.

### 3. Added Navigation for New Hires
- Updated the navbar to show a "My Tasks" link for users with the NEW_HIRE role.
- Added a "My Tasks" button to the dashboard for New Hires.
- Created routes with proper role guards to restrict access to authorized users.