import {Component, OnInit, OnDestroy} from '@angular/core';
import {MessageService} from '../services/messageService';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";
import {interval, Subscription, switchMap} from "rxjs";

@Component({
  selector: 'app-admin',
  standalone: true,
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  imports: [FormsModule, CommonModule, RouterLink,]
})
export class AdminComponent implements OnInit, OnDestroy {
  chatMessages: any[] = [];
  newMessage: string = '';
  adminMessages: any[] = [];
  selectedUser: string = '';
  users: any[] = [];
  private messageSubscription: Subscription = new Subscription();

  constructor(private http: HttpClient, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  ngOnDestroy(): void {
    this.messageSubscription.unsubscribe();
  }

  loadUsers(): void {
    this.messageService.getAllUsers().subscribe(
      (users: any[]) => {
        this.users = users;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  onSelectUser(event: any): void {
    const username = event.target.value;
    this.selectedUser = username;
    this.loadMessages();

    // Subscribe to getUserAndAdminMessages every 5 seconds
    this.messageSubscription = interval(5000).pipe(
      switchMap(() => this.messageService.getUserAndAdminMessages(this.selectedUser))
    ).subscribe(
      (messages: any[]) => {
        this.chatMessages = messages;
	 },
      (error) => {
        console.error('Error fetching messages:', error);
      }
    );
  }


  sendMessage() {
    if (this.newMessage) {
      const message = {
        receiver: this.selectedUser,
        message: this.newMessage,
        sender: 'admin'
      };

      this.http.post('http://localhost:8080/api/chat/message', message).subscribe(
        () => {
        },
        (error) => {
          console.error('Error sending message:', error);
        }
      );
      this.newMessage = '';
    }
  }

  loadMessages(): void {
    this.messageService.getAdminMessages(this.selectedUser).subscribe(
      (messages: any[]) => {
        this.adminMessages = messages;
      },
      (error) => {
        console.error('Error fetching admin messages:', error);
      }
    );
  }
}

