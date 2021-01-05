import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {Location} from '@angular/common';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  errorMsg: string;
  loginGroup: FormGroup;
  constructor(private fb: FormBuilder, private userService: UserService, private location: Location) { }

  ngOnInit(): void {
    this.loginGroup = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onSubmit(): void {
    if (this.loginGroup.invalid) {
      return;
    }

    this.userService.login(this.loginGroup.value.email, this.loginGroup.value.password).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          this.errorMsg = `Error: ${error.error.message}`;
        } else {
          this.location.back();
          this.errorMsg = `Error: ${error.message}`;
        }
        return throwError(this.errorMsg);
      })
    ).subscribe(response => {
        if (response.body.status === 200) {
          this.errorMsg = null;
          const token = response.headers.get('Authentication');
          if (token) {
            localStorage.setItem('auth_token', token);
            localStorage.setItem('user_email', this.loginGroup.value.email);
            localStorage.setItem('password', this.loginGroup.value.password);
            window.location.href = '/';
          }
        } else if (response.body.status === 403) {
          console.log(response);
          this.errorMsg = 'Error:' + response.body.message;
        }

    });
  }

}
