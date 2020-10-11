import { AuthService } from './../auth/auth.service';
import { AuthGuard } from './../auth/auth.guard';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  onLogout() {
    this.authService.logout();
  }
}
