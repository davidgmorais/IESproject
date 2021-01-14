import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {CinemaRequest} from '../../models/CinemaRequest';
import {AdminService} from '../../services/admin.service';

@Component({
  selector: 'app-manage-request',
  templateUrl: './manage-request.component.html',
  styleUrls: ['./manage-request.component.css']
})
export class ManageRequestComponent implements OnInit {
  token: string;
  role: string;
  requestList: CinemaRequest[] = [];
  selectedRequest: CinemaRequest;

  totalPages: number;
  currentPage = 1;

constructor(private router: Router, private adminService: AdminService) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.role = localStorage.getItem('user_role');
    if (this.token === 'null' && this.role !== '2' ) {
      this.router.navigateByUrl('/');
    }
    this.getAllRequests();

  }

  acceptRequest(request: CinemaRequest): void {
    this.adminService.acceptRequest(this.token, request.id).subscribe(response => {
      if (response.status === 200) {
        request.processed = true;
        request.accepted = true;
      }
    });
  }

  denyRequest(request: CinemaRequest): void {
    this.adminService.refuseRequest(this.token, request.id).subscribe(response => {
      if (response.status === 200) {
        request.processed = true;
        request.accepted = false;
      }
    });
  }

  private getAllRequests(page: number = 1): void {
    this.adminService.getAllRequest(this.token, page).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.totalPages = response.totalPages;
        this.requestList = response.data as CinemaRequest[];
      }
    });
  }

  selectRequest(request: CinemaRequest): void {
    this.selectedRequest = request;
  }

  back(): void {
    this.selectedRequest = null;
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getAllRequests(this.currentPage);
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.getAllRequests(this.currentPage);
    }
  }
}
