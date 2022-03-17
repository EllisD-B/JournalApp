import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {navbarComponent} from "./components/navbar/navbar.component";
import {newResourceFormComponent} from "./components/new-resource-form/new-resource-form.component";
import {resourceTableComponent} from "./components/resource-table/resource-table.component";
import {editFormComponent} from "./components/edit-form/edit-form.component";
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

@NgModule({
    declarations: [
        AppComponent,
        navbarComponent,
      newResourceFormComponent,
      resourceTableComponent,
      editFormComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgMultiSelectDropDownModule.forRoot(),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
