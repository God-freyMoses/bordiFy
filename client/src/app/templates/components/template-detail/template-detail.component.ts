import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TemplateService } from '../../service/template.service';
import { TemplateType } from '../../model/template.model';
import { NavbarComponent } from '../../../share/components/navbar.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-template-detail',
  templateUrl: './template-detail.component.html',
  styleUrls: ['./template-detail.component.css'],
  imports: [NavbarComponent, CommonModule, ReactiveFormsModule, RouterModule],
  standalone: true
})
export class TemplateDetailComponent implements OnInit {
  templateId: number | null = null;
  template: TemplateType | null = null;
  templateForm: FormGroup;
  loading = false;
  error = '';
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private templateService: TemplateService
  ) {
    this.templateForm = this.fb.group({
      templateName: ['', [Validators.required]],
      templateDescription: [''],
      templateContent: ['', [Validators.required]],
      hrManagerId: [null, [Validators.required]],
      departmentId: [null, [Validators.required]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.templateId = +id;
      this.loadTemplate(this.templateId);
    } else {
      this.isEditMode = true;
    }
  }

  loadTemplate(id: number): void {
    this.loading = true;
    this.templateService.getTemplateById(id).subscribe({
      next: (data) => {
        this.template = data;
        this.templateForm.patchValue({
          templateName: data.templateName,
          templateDescription: data.templateDescription,
          templateContent: data.templateContent,
          hrManagerId: data.hrManagerId,
          departmentId: data.departmentId
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load template. Please try again.';
        this.loading = false;
        console.error('Error loading template:', err);
      }
    });
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode;
  }

  onSubmit(): void {
    if (this.templateForm.invalid) {
      return;
    }

    this.loading = true;
    const formData = this.templateForm.value;

    if (this.templateId) {
      // Update existing template
      this.templateService.updateTemplate(this.templateId, formData).subscribe({
        next: (data) => {
          this.template = data;
          this.isEditMode = false;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to update template. Please try again.';
          this.loading = false;
          console.error('Error updating template:', err);
        }
      });
    } else {
      // Create new template
      this.templateService.createTemplate(formData).subscribe({
        next: (data) => {
          this.router.navigate(['/templates', data.templateId]);
        },
        error: (err) => {
          this.error = 'Failed to create template. Please try again.';
          this.loading = false;
          console.error('Error creating template:', err);
        }
      });
    }
  }

  cancel(): void {
    if (this.templateId) {
      this.isEditMode = false;
      this.templateForm.patchValue(this.template as any);
    } else {
      this.router.navigate(['/templates']);
    }
  }
}