import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { NotificacionService } from '@core/service/notificacion.service';
import { UserModel } from '@feature/login/shared/model/request/user-model';
import { UsersService } from '../create-user/shared/services/users/users.service';

@Component({
  selector: 'list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss'],
})
export class ListUsersComponent implements OnInit, OnDestroy {

  listUser: UserModel[] = [];
  allUsers: UserModel[] = [];
  searchTerm = '';
  userDelete: UserModel | null = null;
  currentPage = 1;
  totalPages = 1;
  readonly ITEMS_PER_PAGE = 6;

  @ViewChild('myDialog') dialog!: ElementRef<HTMLDialogElement>;

  constructor(
    private userService: UsersService, 
    private notificationService: NotificacionService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.loadUsers();
    window.addEventListener('storage', this.handleStorageChange.bind(this));
    window.addEventListener('focus', this.handleWindowFocus.bind(this));
  }

  ngOnDestroy() {
    window.removeEventListener('storage', this.handleStorageChange.bind(this));
    window.removeEventListener('focus', this.handleWindowFocus.bind(this));
  }

  private handleStorageChange(event: StorageEvent): void {
    if (event.key === 'createdUsers') {
      this.loadUsers();
    }
  }

  private handleWindowFocus(): void {
    this.loadUsers();
  }

  private async loadUsers(): Promise<void> {
    try {
      const response = await this.userService.getUsers();
      const apiUsers = response?.data || [];
      this.finalizeUsers(apiUsers);
    } catch (error) {
      console.error('Error loading users from API:', error);
      this.loadUsersFromLocalStorage();
    }
  }

  private loadUsersFromLocalStorage(): void {
    const users = JSON.parse(localStorage.getItem('createdUsers') || '[]');
    this.finalizeUsers(users);
  }

  private finalizeUsers(apiUsers: UserModel[]): void {
    this.allUsers = this.mergeLocalAndApiUsers(apiUsers);
    this.totalPages = Math.ceil(this.allUsers.length / this.ITEMS_PER_PAGE);
    this.showPage(1);
  }

  private mergeLocalAndApiUsers(apiUsers: UserModel[]): UserModel[] {
    const localUsers = this.getLocalUsers();
    return [...localUsers, ...apiUsers].filter(
      (user, index, self) => index === self.findIndex((u) => u.id === user.id)
    );
  }

  private getLocalUsers(): UserModel[] {
    const users = localStorage.getItem('createdUsers');
    if (!users) return [];

    try {
      return JSON.parse(users).map((user: any) => ({
        id: user.id,
        first_name: user.first_name || user.name?.split(' ')[0] || 'Unknown',
        last_name: user.last_name || user.name?.split(' ')[1] || 'Unknown',
        email: user.email || `${user.name?.replace(' ', '.').toLowerCase()}@example.com`,
        avatar: user.avatar || 'https://reqres.in/img/faces/1-image.jpg',
      }));
    } catch (error) {
      console.error('Error parsing localStorage data:', error);
      return [];
    }
  }

  private showPage(page: number): void {
    this.currentPage = page;
    let filteredUsers = this.allUsers;
    
    if (this.searchTerm && this.searchTerm.length >= 3) {
      filteredUsers = this.allUsers.filter(user =>
        user.first_name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    
    const start = (page - 1) * this.ITEMS_PER_PAGE;
    this.listUser = filteredUsers.slice(start, start + this.ITEMS_PER_PAGE);
    this.totalPages = Math.ceil(filteredUsers.length / this.ITEMS_PER_PAGE);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) this.showPage(this.currentPage + 1);
  }

  previousPage(): void {
    if (this.currentPage > 1) this.showPage(this.currentPage - 1);
  }

  getPageNumbers(): number[] {
    const start = Math.max(1, this.currentPage - 2);
    const end = Math.min(this.totalPages, this.currentPage + 2);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  async deleteUser(user?: UserModel) {
    const userToDelete = user || this.userDelete;
    if (!userToDelete) return;

    const userName = `${userToDelete.first_name} ${userToDelete.last_name}`;

    try {
      const response = await this.userService.deleteUserForIndex(userToDelete.id);
      const isSuccess = response.statusText === "OK";
      const message = isSuccess 
        ? `The user ${userName} was successfully deleted`
        : `Failed to delete the user ${userName}`;
      this.notificationService.notify({ type: isSuccess ? 'success' : 'error', message });
      
      if (isSuccess) {
        this.allUsers = this.allUsers.filter(userItem => userItem.id !== userToDelete.id);
        
        this.removeUserFromLocalStorage(userToDelete.id);
        
        this.filterAndPaginateUsers();
      }
    } catch (error) {
      this.notificationService.notify({ type: 'error', message: `An error occurred while deleting the user ${userName}` });
      console.error('Error:', error);
    }
    
    if (this.dialog?.nativeElement.open) {
      this.closeDialog();
    }
  }

  private removeUserFromLocalStorage(userId: number): void {
    try {
      const existingUsers = JSON.parse(localStorage.getItem('createdUsers') || '[]');
      const updatedUsers = existingUsers.filter((user: any) => user.id !== userId);
      localStorage.setItem('createdUsers', JSON.stringify(updatedUsers));
    } catch (error) {
      console.error('Error removing user from localStorage:', error);
    }
  }

  openDialog(user: UserModel) {
    this.userDelete = user;
    this.dialog?.nativeElement.showModal();
  }

  closeDialog() {
    this.userDelete = null;
    this.dialog?.nativeElement.close();
  }

  onSearchInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.searchTerm = target.value;
    this.filterAndPaginateUsers();
  }

  private filterAndPaginateUsers(): void {
    let filteredUsers = this.allUsers;
    
    if (this.searchTerm && this.searchTerm.length >= 3) {
      filteredUsers = this.allUsers.filter(user =>
        user.first_name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    
    this.totalPages = Math.ceil(filteredUsers.length / this.ITEMS_PER_PAGE);
    this.currentPage = Math.min(this.currentPage, this.totalPages || 1);
    const start = (this.currentPage - 1) * this.ITEMS_PER_PAGE;
    this.listUser = filteredUsers.slice(start, start + this.ITEMS_PER_PAGE);
  }
}



