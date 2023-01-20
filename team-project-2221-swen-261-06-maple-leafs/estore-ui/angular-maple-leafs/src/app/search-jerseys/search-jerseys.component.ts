import { Component, OnInit } from '@angular/core';
import { Jersey } from '../jersey';
import { JerseyService } from '../jersey.service';

import { Observable, Subject } from 'rxjs';

import {
   delay
 } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-jerseys',
  templateUrl: './search-jerseys.component.html',
  styleUrls: ['./search-jerseys.component.css']
})
export class SearchJerseysComponent implements OnInit {

  jerseys$!: Observable<Jersey[]>;
  private searchTerms = new Subject<string>();
  username: string = "";


  /**
   * when instantiate browse-jersey
   * @param jerseyService the service to talk to jersey API
   */
  constructor(private jerseyService: JerseyService, private route: ActivatedRoute) { }

  /**
   * Made the searchbar an keyup event instead of oninit
   * @param term 
   */
  search(term: any): void {
    this.jerseys$ = this.jerseyService.searchJerseys(term.target.value).pipe(delay(100));
  }

  /**
   * Had to change to first get all jerseys to fix the not loading oninit
   */
  ngOnInit(): void {
    this.getJerseys();
    this.username = String(this.route.snapshot.paramMap.get('username'));
  }

  getJerseys(): void {
    this.jerseys$ = this.jerseyService.getJerseys();
  }

}
