import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {forkJoin, map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  baseUrl : string = "http://localhost:8080"
  constructor(private http: HttpClient) {}

  getUserMessages(username: string): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl + `/api/chat/messages/${username}`);
  }

  getAdminMessages(username: string): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl + `/api/chat/responses/${username}`);
  }

  getUserAndAdminMessages(username: string): Observable<any[]> {
    return forkJoin([
      this.http.get<any[]>(`${this.baseUrl}/api/chat/messages/${username}`),
      this.http.get<any[]>(`${this.baseUrl}/api/chat/responses/${username}`)
    ]).pipe(
      map(([userMessages, adminResponses]) => {
        // Merge user messages and admin responses
        const allMessages = [...userMessages, ...adminResponses];
        // Sort messages by timestamp
        return allMessages.sort((a, b) => {
          return new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
        });
      })
    );
  }

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/api/chat/users`);
  }
}
