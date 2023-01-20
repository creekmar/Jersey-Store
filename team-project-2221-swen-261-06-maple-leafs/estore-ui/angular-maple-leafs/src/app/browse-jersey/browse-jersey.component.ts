/**
 * Browse Component will show a list of all the jerseys
 * available, and gives a link to their view-jersey page
 */

import { Component, OnInit } from '@angular/core';
import { Jersey } from '../jersey';
import { JerseyService } from '../jersey.service';

@Component({
  selector: 'app-browse-jersey',
  templateUrl: './browse-jersey.component.html',
  styleUrls: ['./browse-jersey.component.css']
})

export class BrowseJerseyComponent implements OnInit {

  owner: boolean = true; //TODO need to do login
  jerseys: Jersey[] = [];

  /**
   * when instantiate browse-jersey
   * @param jerseyService the service to talk to jersey API
   */
  constructor(private jerseyService: JerseyService) { }

  ngOnInit(): void {
    this.getJerseys();
  }

  /**
   * fetches all jerseys from jersey service
   */
  getJerseys(): void {
    this.jerseyService.getJerseys().subscribe(jerseys => this.jerseys = jerseys);
  }

}
