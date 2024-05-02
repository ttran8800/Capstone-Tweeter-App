import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { RegisterRequestPayload } from '../payloads/register-request-payload.payload';
import { RegisterResponsePayload } from '../payloads/register-response-payload.payload';
import { LoginRequestPayload } from '../payloads/login-request-payload.payload';
import { LoginResponsePayload } from '../payloads/login-response-payload.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private Auth_URL = 'http://18.223.180.29.133:9000/api/v1.0/tweets/auth-service';
  // private Auth_URL = 'http://localhost:9000/api/v1.0/tweets/auth-service';
  // private Auth_URL = 'http://api-gateway:31872/api/v1.0/tweets/auth-service';



  constructor(private http: HttpClient) { }

  register(RegisterPayload: RegisterRequestPayload) {
    return this.http.post<RegisterResponsePayload>(`${this.Auth_URL}/register`, RegisterPayload);
  }

  login(LoginPayload: LoginRequestPayload) {
    return this.http.post<LoginResponsePayload>(`${this.Auth_URL}/login`, LoginPayload);
  }

  validateToken() {
    return this.http.post<any>(`${this.Auth_URL}/validateToken`, null);
  }
}
