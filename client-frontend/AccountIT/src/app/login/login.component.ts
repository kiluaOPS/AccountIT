import { AuthService } from "./../auth/auth.service";
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  private formSubmitAttempt: boolean;

  constructor(private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit() {
    this.form = this.fb.group({
      userName: ["", Validators.required],
      password: ["", Validators.required]
    });
  }

  isFieldInvalid(field: string) {
    const value =
      (!this.form.get(field).valid && this.form.get(field).touched) ||
      (this.form.get(field).untouched && this.formSubmitAttempt);
    return value;
  }

  onSubmit() {
    if (this.form.valid) {
      this.authService.login(this.form.value);
    }
    this.formSubmitAttempt = true;
  }
}
