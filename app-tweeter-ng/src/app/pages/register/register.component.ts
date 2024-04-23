import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { RegisterRequestPayload } from 'src/app/payloads/register-request-payload.payload';
import { RegisterResponsePayload } from 'src/app/payloads/register-response-payload.payload';
import { FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  message = ''
  alertClass = ''

  countdown: number = 5;
  redirectMessage: string = 'Redirecting in ';
  isRegister: boolean = false;

  constructor(private fb: FormBuilder, 
    private authService: AuthService,
    private router: Router) { }

  registerForm = this.fb.group({
    firstName: [null, [Validators.required, Validators.minLength(2)]],
    lastName: [null, [Validators.required, Validators.minLength(2)]],
    loginId: [null, [Validators.required, Validators.minLength(2)]],
    email: [null, [Validators.required, Validators.email]],
    contactNumber: [null, [Validators.required, Validators.minLength(9)]],
    password: [null, [Validators.required, Validators.minLength(6)]],
    confirmPassword: [null, [Validators.required, Validators.minLength(6)]]
  }, { validators: this.passwordMatchValidator });

  get firstName() { return this.registerForm.get('firstName') }
  get lastName() { return this.registerForm.get('lastName') }
  get loginId() { return this.registerForm.get('loginId') }
  get email() { return this.registerForm.get('email') }
  get contactNumber() { return this.registerForm.get('contactNumber') }
  get password() { return this.registerForm.get('password') }
  get confirmPassword() { return this.registerForm.get('confirmPassword') }

  passwordMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      control.get('confirmPassword')?.setErrors({ 'passwordMismatch': true });
      return { 'passwordMismatch': true };
    } else {
      return null;
    }
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const registerPayload: RegisterRequestPayload = {
        firstName: this.registerForm.value.firstName!,
        lastName: this.registerForm.value.lastName!,
        loginId: this.registerForm.value.loginId!,
        email: this.registerForm.value.email!,
        contactNumber: this.registerForm.value.contactNumber!,
        password: this.registerForm.value.password!
      }
      this.authService.register(registerPayload).subscribe({
        next: (response) => {
          const message = response.message;
          if (!response.error) {
            this.message = message;
            this.alertClass = 'alert alert-success';
            this.isRegister = true;
          const interval = setInterval(() => {
            this.countdown--;
            if (this.countdown === 0) {
              clearInterval(interval);
            this.redirect();
            this.isRegister = false;
            }
            
          }, 1000)
          } else if (response.error) {
            this.message = message;
            this.alertClass = 'alert alert-danger';
            if (response.errorType === "LoginId") {
              this.registerForm.controls['loginId'].setErrors({ 'LoginId taken': true });
            }
            if (response.errorType === "Email") {
              this.registerForm.controls['email'].setErrors({ 'Email taken': true });
            }
          }
        },
        error: (error) => {
          this.message = "Registration failed due to a network or server error";
          this.alertClass = 'alert alert-danger';
          console.log(error);
        }
      });
    }
  }

  redirect() {
    this.router.navigateByUrl('/login');
  }
}
