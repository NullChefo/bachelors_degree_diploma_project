import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root'
})
export class NotificationService {
  
  /*


	confirm(
		title: string,
		message: string,
		dialogSize: 'sm' | 'lg' = 'sm',
		btnOkText: string = 'OK',
		btnCancelText: string = 'Cancel'): Promise<boolean> {
		const modalRef = this.ngModalService.open(ConfirmationDialogComponent, { size: dialogSize });
		modalRef.componentInstance.title = title;
		modalRef.componentInstance.messages = message;
		modalRef.componentInstance.btnOkText = btnOkText;
		modalRef.componentInstance.btnCancelText = btnCancelText;

		return modalRef.result;
	}

	prompt(message: string, fields: Field[], size: 'sm' | 'lg'): Promise<string> {
		const modalRef = this.ngModalService.open(PromptDialogComponent, { size, centered: true });
		modalRef.componentInstance.message = message;
		modalRef.componentInstance.fields = fields;

		return modalRef.result;
	}

	errorDialog(error: HttpErrorResponse | Error): void {
		const modalRef = this.ngModalService.open(ErrorDialogComponent, { centered: true });
		modalRef.componentInstance.error = error;
	}
	
	unsavedWarning(): Promise<string> {
		const modalRef = this.ngModalService.open(UnsavedWarningComponent, { size: 'sm', centered: true });
		return modalRef.result;
	}


  */
}