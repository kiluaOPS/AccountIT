import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from "@angular/core";
import { FileUploader, FileItem, FileLikeObject } from "ng2-file-upload";

@Component({
  selector: "app-injuries-upload",
  templateUrl: "./injuries-upload.component.html",
  styleUrls: ["./injuries-upload.component.css"]
})
export class InjuriesUploadComponent implements OnInit, AfterViewInit {
  @ViewChild("injuriesInput", { static: true })
  injuriesInput: ElementRef;

  uploaderInjuries: FileUploader;
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

    this.uploaderInjuries = new FileUploader({
      url: "http://localhost:8080/upload/injuries",
      autoUpload: true,
      headers
    });

    this.uploaderInjuries.onSuccessItem = (
      item: FileItem,
      response: string
    ) => {
      this.uploadSuccess = true;
      this.file = item.file;
      this.isLoading = false;
    };

    this.uploaderInjuries.onErrorItem = (item: FileItem, response: string) => {
      this.uploadError = true;
      this.isLoading = false;
    };
  }

  ngAfterViewInit() {
    this.uploaderInjuries.onAfterAddingFile = item => {
      item.withCredentials = false;
      this.isLoading = true;
    };
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  injuriesFileClicked() {
    this.injuriesInput.nativeElement.click();
  }
}
