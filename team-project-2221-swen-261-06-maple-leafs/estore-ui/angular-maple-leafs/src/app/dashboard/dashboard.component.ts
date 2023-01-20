import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  username: string = "";

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.isAdmin();
  }

  isAdmin(): boolean {
    this.username = String(this.route.snapshot.paramMap.get('username'));
    if(this.username == "admin") {
      return true;
    }
    return false;
  }

}
