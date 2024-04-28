import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from '../services/messageService';
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {HttpClient} from '@angular/common/http';
import {interval, Subscription, switchMap, of, catchError} from "rxjs";

@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  imports: [FormsModule, CommonModule],
})
export class UserComponent implements OnInit, OnDestroy {
  userName: string = '';
  chatMessages: any[] = [];
  newMessage: string = '';
  chatStarted: boolean = false;
  userMessages: any[] = [];


  private messageSubscription: Subscription = new Subscription();

  constructor(private http: HttpClient, private messageService: MessageService,) {
  }

  startChat() {
    if (this.userName) {
      this.chatStarted = true;
    }
  }

  ngOnInit(): void {
    // Setup interval to call loadMessages every 5 seconds
    this.messageSubscription = interval(5000).pipe(
      switchMap(() => {
        if (this.userName) {
          console.log("a username has been set");
          return this.messageService.getUserAndAdminMessages(this.userName).pipe(
            catchError(error => {
              console.error('Error fetching user messages:', error);
              return of([]);
            })
          );
        } else {
          console.log("no username");
          return of([]);
        }
      })
    ).subscribe(
      (messages: any[]) => {
        this.chatMessages = messages;
        console.log("Messages received:", messages);
      }
    );
  }
  ngOnDestroy(): void {
    // Unsubscribe from messageSubscription to prevent memory leaks
    this.messageSubscription.unsubscribe();
  }

  sendMessage() {
    if (this.newMessage && this.userName) {
      const message = {
        receiver: "admin",
        message: this.newMessage,
        sender: this.userName
      };

      // Send message to the server
      this.http.post('http://localhost:8080/api/chat/message', message).subscribe(
        () => {
          console.log('One Message sent successfully');
        },
        (error) => {
          console.error('One Error sending message:', error);
        }
      );

      this.chatMessages.push(message);
      this.newMessage = '';
    }
  }
}
