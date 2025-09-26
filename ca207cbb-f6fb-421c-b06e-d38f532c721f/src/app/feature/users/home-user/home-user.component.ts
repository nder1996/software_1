import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-user',
  templateUrl: './home-user.component.html',
  styleUrls: ['./home-user.component.scss']
})
export class HomeUserComponent implements OnInit {

  constructor() { }

  showNotification: boolean = false;
  notificationMessage: string = '';

  show(message: string) {
    this.notificationMessage = message;
    this.showNotification = true;
  }

  closeNotification() {
    this.showNotification = false;
  }

  ngOnInit(): void {
  }

}
