import { Component, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Router, RouterModule } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private router: Router, private userService: UserService){}

  tempUser:User = {
    id: 0,
    username: "user",
    cart: [],
    name: "user",
  }

  isuser: boolean = false;
  isadmin: boolean = false;
  loggedIn: boolean = false;

  adminUsername: string = "admin";
  userUsername: string = "user";

  message: String = "Please Enter Username";

  checker (htmlUsername: string): boolean {
    if ( htmlUsername==this.adminUsername ){
      this.isadmin = true;
      this.loggedIn = true;
      this.message = "Logged In";
      this.user(htmlUsername);
      return true;
    } else {
      this.tempUser.username = htmlUsername;
      this.userService.getUser(this.tempUser).subscribe(user1 => {
        this.message = "Logging In";
        this.user(htmlUsername);
        return true;
      });
      this.message = "Incorrect Username";
      return false;
    }

  };

  signup(htmlUsername: string): boolean{
    this.tempUser.username = htmlUsername;
    
    (this.userService.addUser(this.tempUser)).subscribe(user => {
        this.message = "Signed Up";
        return true;
    })
    this.message = "Select Another Username";
    return false;
  }

  user(username: String):void {
    this.router.navigateByUrl("/user/"+ username);
  }

  ngOnInit(): void {
  }

}
