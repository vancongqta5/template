import { Component } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-popup-confirm',
  templateUrl: './popup-confirm.component.html',
  styleUrls: ['./popup-confirm.component.scss']
})
export class PopupConfirmComponent {
  title = '';
  content = '';
  status!: number

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
  ) { }

  ngOnInit(): void {
    this.title = this.config.data.title;
    this.content = this.config.data.content;
    this.status = this.config.data.status;
  }

  onSubmit() {
    this.ref.close('yes');
  }
}
