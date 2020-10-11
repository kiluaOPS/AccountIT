import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from "@angular/core";
import { FileUploader, FileItem, FileLikeObject } from "ng2-file-upload";

@Component({
  selector: "app-appointments-upload",
  templateUrl: "./appointments-upload.component.html",
  styleUrls: ["./appointments-upload.component.css"]
})
export class AppointmentsUploadComponent implements OnInit, AfterViewInit {
  @ViewChild("appointmentsInput", { static: true })
  appointmentsInput: ElementRef;

  uploaderAppointments: FileUploader;
  uploadSuccess = false;
  file: FileLikeObject;
  isDropOver: boolean;
  isLoading = false;
  uploadError = false;

  ngOnInit(): void {
    const headers = [
      {
        name: "Accept",
        value: "application/json"
      }
    ];

    this.uploaderAppointments = new FileUploader({
      url: "http://localhost:8080/upload/appointments",
      autoUpload: true,
      headers
    });

    this.uploaderAppointments.onSuccessItem = (
      item: FileItem,
      response: string
    ) => {
      this.uploadSuccess = true;
      this.file = item.file;
      this.isLoading = false;
    };
    this.uploaderAppointments.onErrorItem = (
      item: FileItem,
      response: string
    ) => {
      this.uploadError = true;
      this.isLoading = false;
    };
  }

  ngAfterViewInit() {
    this.uploaderAppointments.onAfterAddingFile = item => {
      item.withCredentials = false;
      this.isLoading = true;
    };
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  appointmentsFileClicked() {
    this.appointmentsInput.nativeElement.click();
  }
}
