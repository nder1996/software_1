import { Component, OnInit } from '@angular/core';
import { AppNotification, NotificacionService } from '@core/service/notificacion.service';

@Component({
  selector: 'app-notification',
  template: `
  <div class="notification-container">
    <div *ngFor="let notification of notifications; let i = index" 
         class="notification" 
         [ngClass]="notification.type"
         [style.transform]="'translateY(' + (i * -70) + 'px)'">
      <span>{{ notification.message }}</span>
      <button class="close-btn" (click)="removeNotification(notification)">×</button>
    </div>
  </div>
`,
styles: [`
  .notification-container {
    position: fixed;
    bottom: 20px;
    right: 20px;
    z-index: 9999;
    max-width: 400px;
  }
  
  .notification {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 20px;
    margin-bottom: 12px;
    border-radius: 12px;
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    position: relative;
    min-width: 320px;
    font-size: 14px;
    line-height: 1.5;
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.2);
  }
  
  .notification:hover {
    transform: translateX(-8px) scale(1.02);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
  }
  
  .close-btn {
    background: rgba(255, 255, 255, 0.3);
    border: none;
    font-size: 16px;
    cursor: pointer;
    margin-left: 15px;
    opacity: 0.8;
    transition: all 0.2s ease;
    padding: 6px;
    width: 28px;
    height: 28px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    color: inherit;
    font-weight: bold;
  }
  
  .close-btn:hover {
    opacity: 1;
    background: rgba(255, 255, 255, 0.5);
    transform: scale(1.1);
  }
  
  .success { 
    background: linear-gradient(135deg, #e8f5e8 0%, #d4f1d4 100%); 
    color: #2d5a2d; 
    border-left: 4px solid #7bc97b;
    box-shadow: 0 4px 12px rgba(123, 201, 123, 0.2);
  }
  
  .error { 
    background: linear-gradient(135deg, #ffe8e8 0%, #ffd4d4 100%); 
    color: #5a2d2d; 
    border-left: 4px solid #ff8a8a;
    box-shadow: 0 4px 12px rgba(255, 138, 138, 0.2);
  }
  
  .info { 
    background: linear-gradient(135deg, #e8f4ff 0%, #d4ebff 100%); 
    color: #2d4a5a; 
    border-left: 4px solid #7bb8ff;
    box-shadow: 0 4px 12px rgba(123, 184, 255, 0.2);
  }
  
  .warning { 
    background: linear-gradient(135deg, #fff8e8 0%, #ffeed4 100%); 
    color: #5a4a2d; 
    border-left: 4px solid #ffcc7b;
    box-shadow: 0 4px 12px rgba(255, 204, 123, 0.2);
  }
`]
})
export class NotificationComponent implements OnInit {

  notifications: AppNotification[] = [];

  constructor(private notificationService: NotificacionService) {}

  ngOnInit() {
    this.notificationService.notifications$.subscribe(notification => {
      this.notifications.push(notification);
      setTimeout(() => this.removeNotification(notification), 5000);
    });
  }

  removeNotification(notification: AppNotification) {
    const index = this.notifications.findIndex(n => n === notification);
    if (index > -1) {
      this.notifications.splice(index, 1);
    }
  }

}