import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterUserByNamePipe } from '@shared/pipes/filter-user-by-name/filter-user-by-name.pipe';
import { NotificationComponent } from './notificacion/notification.component';


@NgModule({
  declarations: [NotificationComponent, FilterUserByNamePipe],
  imports: [CommonModule],
  exports: [NotificationComponent, FilterUserByNamePipe]
})
export class SharedModule {
}
