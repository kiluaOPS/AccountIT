import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from "@angular/core";
import { FileUploader, FileItem, FileLikeObject } from "ng2-file-upload";

@Component({
  selector: "app-customers-upload",
  templateUrl: "./customers-upload.component.html",
  styleUrls: ["./customers-upload.component.css"]
})
export class CustomersUploadComponent implements OnInit, AfterViewInit {
  @ViewChild("customersInput", { static: true })
  customersInput: ElementRef;

  uploaderCustomers: FileUploader;
  uploadSuccess = false;
  uploadError = false;
  isLoading = false;
  file: FileLikeObject;
  isDropOver: boolean;

  ngOnInit(): void {
    const headers = [
      {
        name: "Accept",
        value: "application/json"
      }
    ];

    this.uploaderCustomers = new FileUploader({
      url: "http://localhost:8080/upload/customers",
      autoUpload: true,
      headers
    });

    this.uploaderCustomers.onSuccessItem = (
      item: FileItem,
      response: string
    ) => {
      this.uploadSuccess = true;
      this.file = item.file;
      this.isLoading = false;
    };

    this.uploaderCustomers.onErrorItem = (item: FileItem, response: string) => {
      this.uploadError = true;
      this.isLoading = false;
    };
  }

  ngAfterViewInit() {
    this.uploaderCustomers.onAfterAddingFile = item => {
      item.withCredentials = false;
      this.isLoading = true;
    };
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  customersFileClicked() {
    this.customersInput.nativeElement.click();
  }
}
