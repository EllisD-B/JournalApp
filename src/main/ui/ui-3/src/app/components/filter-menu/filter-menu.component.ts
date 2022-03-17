import {Component, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AppService} from "../../app.service";
import {Subject, takeUntil} from "rxjs";

interface SelectItem {
  id: number;
  tag: string;
}

@Component({
  selector: 'filter-menu',
  templateUrl: 'filter-menu.component.html',
  styleUrls: ['filter-menu.component.css']
  }
)

export class filterMenuComponent implements OnInit {

  dropdownList = Array<SelectItem>();

  selectedItems = Array<SelectItem>();

  dropdownSettings = {};

  isCollapsed = true;
  resources: any;

  filterMenuForm = new FormGroup({
    tags: new FormControl('')
  })

  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private appService: AppService) {
  }

  onSubmit() {
    console.log(this.filterMenuForm.get('tags')?.value);
    let tagString = '';
    for(let i = 0; i < this.filterMenuForm.get('tags')?.value.length; i++) {
      tagString = tagString.concat(this.filterMenuForm.get('tags')?.value[i].tag + ', ');
      console.log(tagString)
    }

    this.appService.filterResourcesByTags(tagString)
      .pipe(takeUntil(this.destroy$))
      .subscribe((resources: any=[]) => {
        console.log('message::::', resources);
        this.resources = resources.data;
      }
    )
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
