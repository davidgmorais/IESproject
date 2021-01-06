import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';

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

  constructor(private fb: FormBuilder, private userService: UserService) { }

  ngOnInit(): void {
    this.changePwdGroup = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
    this.userEmail = localStorage.getItem('user_email');
  }

  changePassword(): void  {
    if (this.changePwdGroup.invalid) {
      this.errorMsg = 'Fill all the fields'
      return;
    }

    if (this.changePwdGroup.value.newPassword !== this.changePwdGroup.value.confirmPassword) {
      this.errorMsg = 'The passwords don\'t match';
      return;
    }

    if (this.changePwdGroup.value.oldPassword !== localStorage.getItem('password')) {
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
}
