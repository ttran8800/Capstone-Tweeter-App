import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, AbstractControl, FormGroup } from '@angular/forms';
import { DataService } from 'src/app/services/data.service';
import { IUser } from 'src/app/models/user.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit{

  user: IUser | null = null;
  private subscription: Subscription = new Subscription();

  profileForm!: FormGroup;

  message = ''
  alertClass = ''
  countdown: number = 5;

  isDisabled = true;

  constructor(private fb: FormBuilder, private dataService: DataService) { }

  ngOnInit(): void {
    this.subscription.add(
      this.dataService.user$.subscribe(user => {
        this.user = user;
        this.initializeForm(user);
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  get firstName(): AbstractControl {
    return this.profileForm.get('firstName')!;
  }

  get lastName(): AbstractControl {
    return this.profileForm.get('lastName')!;
  }

  get contactNumber(): AbstractControl {
    return this.profileForm.get('contactNumber')!;
  }

  get password(): AbstractControl {
    return this.profileForm.get('password')!;
  }

  get confirmPassword(): AbstractControl {
    return this.profileForm.get('confirmPassword')!;
  }

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

  initializeForm(user: IUser | null) {
    this.profileForm = this.fb.group({
      firstName: [user?.firstName, [Validators.required, Validators.minLength(2)]],
      lastName: [user?.lastName, [Validators.required, Validators.minLength(2)]],
      contactNumber: [user?.contactNumber, [Validators.required, Validators.minLength(9)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    }, { validators: this.passwordMatchValidator });
  }

  onSubmitHandler() {
    if (this.profileForm.valid && this.user) {
      const updateUser: IUser = {
        firstName: this.profileForm.value.firstName,
        lastName: this.profileForm.value.lastName,
        contactNumber: this.profileForm.value.contactNumber!,
        password: this.profileForm.value.password!
      }
      this.dataService.updateUser(updateUser);
      this.message = "User profile updated";
      this.alertClass = 'alert alert-success';
      const interval = setInterval(() => {
        this.countdown--;
        if (this.countdown === 0) {
          clearInterval(interval);
          this.message = '';
          this.alertClass = '';
        }
        
      }, 1000)
    }
  }
}
