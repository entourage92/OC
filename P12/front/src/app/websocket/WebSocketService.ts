import { Injectable } from '@angular/core';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient!: Stomp.Client;

  constructor(private http: HttpClient) {
    this.connect(); // Initialize WebSocket connection in constructor
  }

  private connect() {
    const socket = new SockJS('http://localhost:8080/chat');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      console.log('WebSocket connected');
    });
  }

  sendMessage(message: any): Observable<any> {
    return this.http.post('http://localhost:8080/api/users/message', message);
  }

  receiveMessages(): Observable<any> {
    return new Observable(observer => {
      this.stompClient.subscribe('/chat/messages', (message: any) => {
        observer.next(JSON.parse(message.body));
      });
    });
  }
}
