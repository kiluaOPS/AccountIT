import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root"
})
export class BasicAuthenticationService {
  constructor() {}

  authenticate(username, password) {
    if (username === "marta" && password === "qazwsx") {
      return true;
    }
    return false;
  }
}
