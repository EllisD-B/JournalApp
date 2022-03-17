import {Component, Input, OnDestroy} from '@angular/core';
import {AppService} from "../../app.service";
import {Observable, Subject} from "rxjs";
import {switchMap, takeUntil} from "rxjs/operators";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";

interface SelectItem {
  id: number;
  tag: string;
}

@Component({
  selector: 'resource-table',
  templateUrl: 'resource-table.component.html',
  styleUrls: ['resource-table.component.css']
})

export class resourceTableComponent implements OnDestroy {

  constructor(private appService: AppService, private route: ActivatedRoute) {
  }

  resources: Array<any> = [];
  resources$: Observable<any>;
  selectedId: number;
  currentResource: any;
  resourcesCount = 0;
  isCollapsedArray = Array<boolean>();
  isCollapsed = false;
  @Input() refresh: boolean;

  dropdownList = Array<SelectItem>();

  selectedItems = Array<SelectItem>();

  dropdownSettings = {};

  destroy$: Subject<boolean> = new Subject<boolean>();

  filterMenuForm = new FormGroup({
    tags: new FormControl('')
  })

  setCollapsed() {
    for(let i = 0; i < this.resources.length; i++) {
      this.isCollapsedArray[i] = true;
    }
  }

  getAllResources() {
    // @ts-ignore
    this.appService.getResources()
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (resources: any=[]) => {
          this.resourcesCount = resources.data.length;
          this.resources = resources.data;
          console.log('Get all resources: ')
          console.log(resources)
        }
      )
  }

  onSubmit() {
    console.log(this.filterMenuForm.get('tags')?.value);
    let tagString = '';
    for(let i = 0; i < this.filterMenuForm.get('tags')?.value.length; i++) {
      tagString = tagString.concat(this.filterMenuForm.get('tags')?.value[i].tag + ', ');
    }

    if(this.filterMenuForm.get('tags')?.value.length === 0) {
      this.getAllResources();
    } else {
      this.appService.filterResourcesByTags(tagString)
        .pipe(takeUntil(this.destroy$))
        .subscribe((resources: any=[]) => {
          console.log('message::::', resources);
          this.resources = resources.data;
        });
    }
  }

  filterResources(tags: any) {
    this.appService.filterResourcesByTags(tags)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (resources: any=[]) => {
          console.log(resources.data);
          this.resources = resources.data;
      })
  }

  onEdit() {
    this.getAllResources();
  }

  onClickDelete(resource: any) {
    console.log(resource);
    console.log("ID: " + resource.id);
    this.appService.removeResource(resource).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message:::', data);
      this.resourcesCount = this.resourcesCount-1;
      this.getAllResources();
    })
  }

  setCurrentResource(resource: any) {
    this.appService.setCurrentResource(resource);
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe()
  }

  ngOnInit() {
    this.getAllResources();

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

    this.resources$ = this.route.paramMap.pipe(
      switchMap(params => {
        this.selectedId = Number(params.get('id'));
        this.setCollapsed();
        return this.appService.getResources();
      })
    )
  }
}
