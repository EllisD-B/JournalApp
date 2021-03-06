import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {AppService} from "./app.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Subject, takeUntil} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

interface SelectItem {
  id: number;
  tag: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnDestroy, OnInit {

  constructor(private appService: AppService) {}

  title = 'journalUi';

  public isCollapsed = false;

  dropdownList = Array<SelectItem>();

  selectedItems = Array<SelectItem>();

  dropdownSettings = {};

  resources: Array<any> = [];
  public refresh = false;
  destroy$: Subject<boolean> = new Subject<boolean>();

  filterMenuForm = new FormGroup({
    tags: new FormControl('')
  })

  onSubmit() {
    console.log(this.filterMenuForm.get('tags')?.value);
    let tagString = '';
    for(let i = 0; i < this.filterMenuForm.get('tags')?.value.length; i++) {
      tagString = tagString.concat(this.filterMenuForm.get('tags')?.value[i].tag + ', ');
    }

    return tagString;
  }

  getAllResources() {

  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe()
  }

  ngOnInit() {
    this.dropdownList = [
      { id: 1, tag: 'Java' },
      { id: 2, tag: 'Python' },
      { id: 3, tag: 'JS' },
      { id: 4, tag: 'HTML/CSS' },
      { id: 5, tag: 'DevOps' },
      { id: 6, tag: 'Testing' },
      { id: 7, tag: 'Data Science' },
      { id: 8, tag: 'Databases' }
    ];
    this.selectedItems = [];
    this.dropdownSettings =
      {
        singleSelection: false,
        idField: 'id',
        textField: 'tag',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        itemsShowLimit: 3,
        allowSearchFilter: true,
        limitSelection: 3
      };
  }

}
