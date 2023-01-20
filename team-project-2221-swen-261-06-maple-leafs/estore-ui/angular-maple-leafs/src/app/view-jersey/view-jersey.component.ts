/**
 * view-jersey component. Get a jersey from an API and 
 * display it. This is also the component that has the interface
 * to buy, update, or delete jersey
 */

import { Component, OnInit } from '@angular/core';
import { Jersey, Size } from '../jersey';
import { JerseyService } from '../jersey.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { debounceTime } from 'rxjs';
import { UserService } from '../user.service';
import { MessagesService } from '../messages.service';

@Component({
  selector: 'app-view-jersey',
  templateUrl: './view-jersey.component.html',
  styleUrls: ['./view-jersey.component.css']
})

export class ViewJerseyComponent implements OnInit {

  sizeList: String[] = [Size[0], Size[1], Size[2], Size[3]];
  jersey: Jersey | undefined;
  name: string = "test2"; //TODO
  owner: boolean = true; //TODO need to do login stuff
  home: boolean | undefined;
  discount: number = 0;
  selectedSize: Size = Size.SMALL; //default start at small size
  jerseys: Jersey[] = [];
  messages: string[] = [];
  username: string = "";

  /**
   * When a view-jersey component is created
   * @param route to be able to read the route
   * @param jerseyService service that connects to jersey API
   * @param location to be able to change url routes
   */
  constructor(private route: ActivatedRoute, 
    private jerseyService: JerseyService, 
    private location: Location,
    private router: Router,
    private userService: UserService,
    public messageService: MessagesService) { }

  ngOnInit(): void {
    this.getJersey();
  }

  /**
   * Gets specified jersey (id) from the jerseyService
   */
  getJersey(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(id);
    this.jerseyService.getJersey(id).subscribe(jersey => this.jersey = jersey);
  }

  /**
   * Will go back to the previous page
   */
  back(): void {
    this.location.back();
  }

  getEditPage(){
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.router.navigateByUrl("edit/" + id);
  }

  /**
   * To delete the specified jersey
   */
  delete(): void {
    const id = this.jersey?.id;
    if(id != undefined) {
      if(confirm("Are you sure you want to delete " + this.jersey?.name +
       " " + this.jersey?.number + "?")) {
        console.log(id);
        this.jerseyService.deleteJersey(id).subscribe();
        window.location.reload();
       }
    }
    
  }

  /**
   * Change the size that was selected
   * @param size 
   */
  changeSize(size: string) {
    this.selectedSize = size as unknown as Size;
    console.log("Selected Size: " + this.selectedSize);
  }

  /**
   * add jersey to cart
   */
  addToCart(): void {
    if(this.jersey) {
      const newJersey: Jersey = {
        id: this.jersey.id,
        name: this.jersey.name,
        number: this.jersey.number,
        cost: this.jersey.cost,
        size: this.selectedSize,
        home: this.jersey.home,
        discount: this.jersey.discount
      }
      this.userService.addToCart(this.username, newJersey).subscribe(jersey => {this.jerseys.push(jersey);});
      this.messageService.add("Added Jersey with id " + newJersey.id + " to cart");
    }

  }

  isAdmin(): boolean {
    this.username = String(this.route.snapshot.paramMap.get('user'));
    if(this.username == "admin") {
      return true;
    }
    return false;
  }

}
