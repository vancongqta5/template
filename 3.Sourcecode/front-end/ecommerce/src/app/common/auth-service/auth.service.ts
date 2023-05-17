import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  login() {
    throw new Error('Method not implemented.');
  }
  setToken(token: any) {
    throw new Error('Method not implemented.');
  }

  constructor(private http: HttpClient) { }

  private refrToken!: string;
  
  public refreshToken(): Observable<any> {
    // gọi API để lấy token mới từ refreshToken
    return this.http.post('/api/refreshToken', { refreshToken: this.refrToken });
  }

  public getRefreshToken(): string {
    return this.refrToken;
  }

}
