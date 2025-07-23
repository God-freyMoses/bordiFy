export type TemplateType = {
  templateId: number;
  templateName: string;
  templateDescription: string;
  templateContent: string;
  hrManagerId: number;
  departmentId: number;
};

export type CreateTemplateRequestType = {
  templateName: string;
  templateDescription: string;
  templateContent: string;
  hrManagerId: number;
  departmentId: number;
};

export type UpdateTemplateRequestType = {
  templateName: string;
  templateDescription: string;
  templateContent: string;
  hrManagerId: number;
  departmentId: number;
};