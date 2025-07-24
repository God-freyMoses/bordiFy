import { Component, OnInit } from '@angular/core';
import { TemplateService } from '../../service/template.service';
import { TemplateType } from '../../model/template.model';
import { NavbarComponent } from '../../../share/components/navbar.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../auth/service/token.service';
import { Store } from '@ngrx/store';
import { selectUser } from '../../../auth/store/auth.selectors';

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.css'],
  imports: [NavbarComponent, CommonModule, RouterModule],
  standalone: true
})
export class TemplateListComponent implements OnInit {
  templates: TemplateType[] = [];
  loading = false;
  error = '';
  userRole = '';

  constructor(
    private templateService: TemplateService,
    private tokenService: TokenService,
    private store: Store
  ) {}

  ngOnInit(): void {
    this.loadTemplates();
    
    // Get user role from store
    this.store.select(selectUser).subscribe(user => {
      if (user) {
        this.userRole = user.role;
      } else {
        // Fallback to token service if store is empty
        this.userRole = this.tokenService.getUserRole() || '';
      }
    });
  }

  loadTemplates(): void {
    this.loading = true;
    this.error = '';
    
    this.templateService.getAllTemplates().subscribe({
      next: (data) => {
        this.templates = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load templates. Please try again.';
        this.loading = false;
        console.error('Error loading templates:', err);
      }
    });
  }

  deleteTemplate(id: number): void {
    if (confirm('Are you sure you want to delete this template?')) {
      this.templateService.deleteTemplate(id).subscribe({
        next: () => {
          this.templates = this.templates.filter(template => template.templateId !== id);
        },
        error: (err) => {
          this.error = 'Failed to delete template. Please try again.';
          console.error('Error deleting template:', err);
        }
      });
    }
  }
}