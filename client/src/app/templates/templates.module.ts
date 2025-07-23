import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { TemplateService } from './service/template.service';

@NgModule({
  imports: [
    HttpClientModule
  ],
  providers: [
    TemplateService
  ]
})
export class TemplatesModule { }