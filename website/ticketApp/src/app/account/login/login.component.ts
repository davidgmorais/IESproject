import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginGroup: FormGroup;
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.loginGroup = this.fb.group({
      email: ['davidmorais35@ua.pt', Validators.required],
      password: ['1234', Validators.required]
    });
  }


  onSubmit(): void {
    if (this.loginGroup.invalid) {
      return;
    }

    this.userService.login(this.loginGroup.value.email, this.loginGroup.value.password).subscribe(response => {
      const token = response.headers.get('Authentication');
      if (token) {
        localStorage.setItem('auth_token', token);
        localStorage.setItem('user_email', this.loginGroup.value.email);
        window.location.href = '/';

      }
    });
  }

}
