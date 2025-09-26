import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NotificacionService } from '@core/service/notificacion.service';
import { UsersService } from '../create-user/shared/services/users/users.service';
import { FilterUserByNamePipe } from '@shared/pipes/filter-user-by-name/filter-user-by-name.pipe';
import { CreateUserComponent } from '../create-user/create-user.component';

import { ListUsersComponent } from './list-users.component';

describe('ListUsersComponent', () => {
  let component: ListUsersComponent;
  let fixture: ComponentFixture<ListUsersComponent>;
  let mockNotificationService: jasmine.SpyObj<NotificacionService>;
  let mockUsersService: jasmine.SpyObj<UsersService>;

  beforeEach(async () => {
    const notificationSpy = jasmine.createSpyObj('NotificacionService', ['notify']);
    const usersSpy = jasmine.createSpyObj('UsersService', ['getUsers', 'deleteUser']);

    await TestBed.configureTestingModule({
      declarations: [ ListUsersComponent, FilterUserByNamePipe ],
      imports: [
        RouterTestingModule.withRoutes([ 
          { path: 'list', component: ListUsersComponent },
          { path: 'create', component: CreateUserComponent },
        ]),
        FormsModule,
        HttpClientTestingModule
      ],
      providers: [
        { provide: NotificacionService, useValue: notificationSpy },
        { provide: UsersService, useValue: usersSpy }
      ]
    })
    .compileComponents();

    mockNotificationService = TestBed.inject(NotificacionService) as jasmine.SpyObj<NotificacionService>;
    mockUsersService = TestBed.inject(UsersService) as jasmine.SpyObj<UsersService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
