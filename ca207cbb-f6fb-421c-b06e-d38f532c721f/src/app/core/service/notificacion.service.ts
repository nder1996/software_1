import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface AppNotification {
  type: 'success' | 'error' | 'info' | 'warning';
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class NotificacionService {
  private notificationsSubject = new Subject<AppNotification>();
  notifications$ = this.notificationsSubject.asObservable();

  constructor() {}

  notify(notification: AppNotification): Promise<void> {
    return new Promise((resolve) => {
      this.notificationsSubject.next(notification);
      resolve();
    });
  }
}