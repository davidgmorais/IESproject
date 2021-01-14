import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {CinemaUser} from '../../models/CinemaUser';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-cinema',
  templateUrl: './add-cinema.component.html',
  styleUrls: ['./add-cinema.component.css']
})
export class AddCinemaComponent implements OnInit {

  signupGroup: FormGroup;
  confirmationGroup: FormGroup;
  registerToken: string;
  errorMsg: string;
  cinemaOwner: CinemaUser;
  constructor(private fb: FormBuilder, private userService: UserService, private route: Router) { }

  ngOnInit(): void {
    this.signupGroup = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      username: ['', Validators.required],
      location: ['', Validators.required],
      description: ['', Validators.required],
      termsAndConditions: ['', Validators.required]
    });
    this.confirmationGroup = this.fb.group({
      code: ['', Validators.required]
    });
  }

  submit(): void{
    if (this.signupGroup.invalid) {
      this.errorMsg = 'Please fill all the fields and accept the terms and conditions, it\'s extremely important.';
      return;
    }

    if (this.signupGroup.value.password !== this.signupGroup.value.confirmPassword) {
      this.errorMsg = 'Make sure your passwords match.';
      return;
    }

    this.errorMsg = null;
    this.cinemaOwner = new CinemaUser(
      this.signupGroup.value.username,
      this.signupGroup.value.email,
      this.signupGroup.value.password,
      this.signupGroup.value.location,
      this.signupGroup.value.description);

    this.userService.registerCinema(this.cinemaOwner).subscribe( response => {
      this.registerToken = response.headers.get('registerToken');
      console.log(response);
    });
  }

  confirm(): void {
    this.userService.confirmCinema(this.registerToken, this.confirmationGroup.value.code).subscribe(response => {
      console.log(response);
      if (response.status === 200) {
        this.route.navigateByUrl('/login');
      } else if (response.status === 400) {
        this.errorMsg = 'Confirmation code does not match';
      }
    });
  }

  resend(): void {
    this.userService.registerCinema(this.cinemaOwner).subscribe( response => {
      this.registerToken = response.headers.get('registerToken');
    });
  }
}
