import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from "@angular/core";
import { FileUploader, FileItem, FileLikeObject } from "ng2-file-upload";

@Component({
  selector: "app-clinics-upload",
  templateUrl: "./clinics-upload.component.html",
  styleUrls: ["./clinics-upload.component.css"]
})
export class ClinicsUploadComponent implements OnInit, AfterViewInit {
  @ViewChild("clinicsInput", { static: true })
  clinicsInput: ElementRef;

  uploaderClinics: FileUploader;
  uploadSuccess = false;
  file: FileLikeObject;
  isDropOver: boolean;

  ngOnInit(): void {
    const headers = [
      {
        name: "Accept",
        value: "application/json"
      }
    ];

    this.uploaderClinics = new FileUploader({
      url: "http://localhost:8080/upload/clinics",
      autoUpload: true,
      headers
    });

    this.uploaderClinics.onSuccessItem = (
      item: FileItem,
      response: string) => {
      this.uploadSuccess = true;
      this.file = item.file;
    };

  }

  ngAfterViewInit() {
    this.uploaderClinics.onAfterAddingFile = item => {
      item.withCredentials = false;
    };
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  clinicsFileClicked() {
    this.clinicsInput.nativeElement.click();
  }
}
