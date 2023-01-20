import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ViewJerseyComponent } from './view-jersey/view-jersey.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { AdminComponent } from './admin/admin.component';
import { BrowseJerseyComponent } from './browse-jersey/browse-jersey.component';
import { FormsModule } from '@angular/forms';
import { AddJerseyComponent } from './add-jersey/add-jersey.component';
import { SearchJerseysComponent } from './search-jerseys/search-jerseys.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { EditJerseyComponent } from './edit-jersey/edit-jersey.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserComponent,
    AdminComponent,
    ViewJerseyComponent,
    LoginComponent, 
    BrowseJerseyComponent,
    AddJerseyComponent,
    SearchJerseysComponent,
    DashboardComponent,
    ShoppingCartComponent,
    EditJerseyComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
