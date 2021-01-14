import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  changePwdGroup: FormGroup;
  changePasswordToggle = false;
  errorMsg: string;
  userEmail: string;
  token: string;

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.changePwdGroup = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });

    this.token = localStorage.getItem('auth_token');
    this.userEmail = localStorage.getItem('user_email');
    if (!this.token || this.token === 'null' || !this.userEmail || this.userEmail === 'null') {
      this.router.navigateByUrl('/');
    }
  }

  changePassword(): void  {
    if (this.changePwdGroup.invalid) {
      this.errorMsg = 'Fill all the fields';
      return;
    }

    if (this.changePwdGroup.value.newPassword !== this.changePwdGroup.value.confirmPassword) {
      this.errorMsg = 'The passwords don\'t match';
      return;
    }

    if (this.changePwdGroup.value.oldPassword !== localStorage.getItem('password')) {
      console.log(localStorage.getItem('password'));
      this.errorMsg = 'The old password doesn\'t match';
      return;
    }

    this.errorMsg = null;
    this.userService.changePassword(localStorage.getItem('auth_token'), this.changePwdGroup.value.newPassword).subscribe(response => {
      console.log(response);
      if (response.status === 200) {
        window.location.href = '/profile';
      }
    });

  }

  logout(): void {
    this.token = null;
    this.userEmail = null;
    localStorage.setItem('auth_token', null);
    localStorage.setItem('user_email', null);
    localStorage.setItem('user_role', null);
    localStorage.setItem('password', null);
    localStorage.setItem('user_id', null);
    window.location.href = '/';
  }
}
