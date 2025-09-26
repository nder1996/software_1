import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotificacionService } from '@core/service/notificacion.service';
import { CreateUser } from '@feature/login/shared/model/request/create-user.model';
import { UsersService } from './shared/services/users/users.service';
import { ResponseCreateUserModel } from '@feature/login/shared/model/response/create-user-response.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss'],
})
export class CreateUserComponent implements OnInit {

  public userNew: CreateUser = new CreateUser();
  public formCreate!: FormGroup;


  constructor(
    private readonly router: Router,private userService:UsersService , private fb: FormBuilder,
    private notificationService: NotificacionService
  ) {
    this.formCreate = this.fb.group({
      name: ['', [Validators.required]],
      job: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    // Inicialización del componente
  }

  public saveUser(){
    if( this.formCreate.valid){
      const name = this.formCreate.get('name')?.value;
      const job = this.formCreate.get('job')?.value;
      if(name && job){
        this.userNew.job = job;
        this.userNew.name = name;
        this.createUser(this.userNew);
      }

    }
  }


  public async createUser(createUser: CreateUser) {
    try {
      const response: ResponseCreateUserModel = await this.userService.createUser(createUser);
      if (response.id) {
        this.saveUserToLocalStorage(response);
        this.notificationService.notify({ type: 'success', message: `The user ${response.name} was created successfully` });
        this.redirectToListUsers();
      } else {
        this.notificationService.notify({ type: 'error', message: `There was an error creating the user ${createUser.name}` });
      }
    } catch (error) {
      this.notificationService.notify({ type: 'error', message: `There was an error creating the user ${createUser.name}` });
      console.error('Error captured:', error);
    }
  }

  private saveUserToLocalStorage(userResponse: ResponseCreateUserModel): void {
    try {
      const existingUsers = JSON.parse(localStorage.getItem('createdUsers') || '[]');
      
      const newUser = {
        id: userResponse.id,
        name: userResponse.name,
        job: userResponse.job,
        first_name: userResponse.name?.split(' ')[0] || userResponse.name || 'Unknown',
        last_name: userResponse.name?.split(' ')[1] || '',
        email: `${userResponse.name?.replace(' ', '.').toLowerCase()}@example.com`,
        avatar: 'https://reqres.in/img/faces/1-image.jpg',
        createdAt: userResponse.createdAt || new Date()
      };
      
      existingUsers.push(newUser);
      
      localStorage.setItem('createdUsers', JSON.stringify(existingUsers));
    } catch (error) {
      console.error('Error saving user to localStorage:', error);
    }
  }

  /**
   * Este método no se puede modificar
   * */
  public redirectToListUsers(): void {
    this.router.navigateByUrl('/users/list');
  }
}
