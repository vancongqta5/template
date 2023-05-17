import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ExcelService } from 'src/app/common/excel-service/excel.service';

@Component({
  selector: 'app-import-file',
  templateUrl: './import-file.component.html',
  styleUrls: ['./import-file.component.scss']
})
export class ImportFileComponent implements OnInit {

URL_IMPORT!: string;
file!: File
    
constructor( private ref: DynamicDialogRef,
             private excelService: ExcelService,
             private config: DynamicDialogConfig,){}
  ngOnInit(): void {
   this.URL_IMPORT = this.config.data.url_import;
  }


  onClose() {
    this.ref.close('close import file!');
  }

  uploadFile(event?: any){
    this.file = event.target.files[0];
    console.log(this.file)
  }

  onSave(){
    if(this.URL_IMPORT && this.file){
         this.excelService.importExcelFile(this.file, this.URL_IMPORT).subscribe(res =>{
          location.reload();
         })
    }
    this.ref.close();
  }
}
