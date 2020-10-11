import { FormGroup } from "@angular/forms";
import { LoginComponent } from "./../login/login.component";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { Router } from "@angular/router";
import { User } from "./user";

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private loggedIn = false;

  constructor(private router: Router) {}

  login(user: User) {
    if (user.userName === "accountit" && user.password === "qwerty") {
      this.loggedIn = true;
      this.router.navigate(["upload"]);
    }
    sessionStorage.setItem("authenticateUser", user.userName);
  }

  logout() {
    this.loggedIn = false;
    this.router.navigate(["login"]);
    sessionStorage.removeItem("authenticateUser");
  }

  isLoggedIn() {
    const user = sessionStorage.getItem("authenticateUser");
    return !(user === null);
  }
}
