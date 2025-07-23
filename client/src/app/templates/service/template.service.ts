import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TemplateType, CreateTemplateRequestType, UpdateTemplateRequestType } from '../model/template.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TemplateService {
  private apiUrl = `${environment.apiUrl}/templates`;

  constructor(private http: HttpClient) {}

  createTemplate(template: CreateTemplateRequestType): Observable<TemplateType> {
    return this.http.post<TemplateType>(this.apiUrl, template);
  }

  getTemplateById(id: number): Observable<TemplateType> {
    return this.http.get<TemplateType>(`${this.apiUrl}/${id}`);
  }

  getAllTemplates(): Observable<TemplateType[]> {
    return this.http.get<TemplateType[]>(this.apiUrl);
  }

  getTemplatesByDepartmentId(departmentId: number): Observable<TemplateType[]> {
    return this.http.get<TemplateType[]>(`${this.apiUrl}/department/${departmentId}`);
  }

  getTemplatesByHrManagerId(hrManagerId: number): Observable<TemplateType[]> {
    return this.http.get<TemplateType[]>(`${this.apiUrl}/hr/${hrManagerId}`);
  }

  updateTemplate(id: number, template: UpdateTemplateRequestType): Observable<TemplateType> {
    return this.http.put<TemplateType>(`${this.apiUrl}/${id}`, template);
  }

  deleteTemplate(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}