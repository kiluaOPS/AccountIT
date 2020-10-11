import { FileItem } from 'ng2-file-upload';
import { FileLikeObject } from 'ng2-file-upload';
import { FileUploader } from 'ng2-file-upload';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-events-upload',
  templateUrl: './events-upload.component.html',
  styleUrls: ['./events-upload.component.css']
})
export class EventsUploadComponent implements OnInit {
  @ViewChild("eventsInput", { static: true })
  eventsInput: ElementRef;

  uploaderEvents: FileUploader;
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

    this.uploaderEvents = new FileUploader({
      url: "http://localhost:8080/upload/events",
      autoUpload: true,
      headers
    });

    this.uploaderEvents.onSuccessItem = (
      item: FileItem,
      response: string
    ) => {
      this.uploadSuccess = true;
      this.file = item.file;
      this.isLoading = false;
    };

    this.uploaderEvents.onErrorItem = (item: FileItem, response: string) => {
      this.uploadError = true;
      this.isLoading = false;
    };
  }

  ngAfterViewInit() {
    this.uploaderEvents.onAfterAddingFile = item => {
      item.withCredentials = false;
      this.isLoading = true;
    };
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  eventsFileClicked() {
    this.eventsInput.nativeElement.click();
  }
}
