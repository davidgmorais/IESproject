import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../models/User';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupGroup: FormGroup;
  confirmationGroup: FormGroup;
  registerToken: string;
  errorMsg: string;
  user: User;

  constructor(private fb: FormBuilder, private userService: UserService, private route: Router) { }

  ngOnInit(): void {
    this.signupGroup = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      username: ['', Validators.required],
      termsAndConditions: ['', Validators.required]
    });
    this.confirmationGroup = this.fb.group({
      code: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.signupGroup.invalid) {
      this.errorMsg = 'Please, fill all of the form and accept the terms and conditions';
      return;
    }

    if (this.signupGroup.value.password !== this.signupGroup.value.confirmPassword) {
      this.errorMsg = 'Confirm correctly your passwords';
      return;
    }

    this.errorMsg = null;
    this.user = new User(this.signupGroup.value.email , this.signupGroup.value.username, this.signupGroup.value.password);
    this.userService.register(this.user).subscribe( response => {
      this.registerToken = response.headers.get('registerToken');
    });

  }

  confirm(): void {
    this.userService.confirm(this.registerToken, this.confirmationGroup.value.code).subscribe(response => {
      if (response.status === 200) {
        this.route.navigateByUrl('/login');
      } else if (response.status === 400) {
        this.errorMsg = 'Confirmation code does not match';
      }
    });
  }

  resend(): void {
    this.userService.register(this.user).subscribe( response => {
      this.registerToken = response.headers.get('registerToken');
    });
  }
}
