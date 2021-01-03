import {Component, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { EventEmitter } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit {

  filterForm: FormGroup;
  genres = this.getGenres();
  @Output() filterEvent = new EventEmitter<any>();

  years = [
    {value: new Date().getFullYear(), name: new Date().getFullYear()},
    {value: new Date().getFullYear() - 1, name: new Date().getFullYear() - 1},
    {value: new Date().getFullYear() - 2, name: new Date().getFullYear() - 2}
  ];

  orderBy = [
    {value: 'new', name: 'New'},
    {value: 'old', name: 'Old'},
    {value: 'popular', name: 'Popular'},
    {value: 'alphabetically', name: 'Alphabetically'},
    {value: 'nearyou', name: 'Near you'}
  ];

  constructor(private fb: FormBuilder, private route: Router, private params: ActivatedRoute) { }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      genre: [null],
      order: ['new'],
      year: [null]
    });

  }

  submit(): void {
    const params: {[key: string]: any} = {};

    if (this.filterForm.value.genre) {
      params.genre = this.filterForm.value.genre;
    }
    if (this.filterForm.value.order !== 'new') {
      params.order = this.filterForm.value.order;
    }
    if (this.filterForm.value.year) {
      params.year = this.filterForm.value.year;
    }

    this.filterEvent.emit(params);
  }

  clean(): void {
    this.filterEvent.emit({clean: true});
    this.route.navigateByUrl('/movielist');
  }

  private getGenres(): any {
    return [
      {value: 'action', name: 'Action'}
    ];
  }
}
