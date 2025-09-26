import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CreateUserComponent } from './create-user/create-user.component';
import { ListUsersComponent } from './list-users/list-users.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '@shared/shared.module';
import { UsersRoutingModule } from './users-routing.module';

@NgModule({
    declarations: [
        ListUsersComponent,
        NavBarComponent,
        CreateUserComponent,
    ],
    imports: [
        CommonModule,
        UsersRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        NavBarComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsersModule {
}
