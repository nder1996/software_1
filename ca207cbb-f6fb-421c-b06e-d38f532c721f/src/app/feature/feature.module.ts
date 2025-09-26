import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FeatureRoutingModule } from './feature-routing.module';
import { SharedModule } from '@shared/shared.module';
import { LoginModule } from './login/login.module';
import { HomeUserComponent } from './users/home-user/home-user.component';
import { UsersModule } from './users/users.module';


@NgModule({
  declarations: [HomeUserComponent],
  imports: [
    CommonModule,
    FeatureRoutingModule,
    UsersModule,
    LoginModule,
    SharedModule
  ],
  exports: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FeatureModule { }
