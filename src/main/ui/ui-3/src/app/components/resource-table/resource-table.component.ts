import {Component, Input, OnDestroy} from '@angular/core';
import {AppService} from "../../app.service";
import {Observable, Subject} from "rxjs";
import {switchMap, takeUntil} from "rxjs/operators";
import {ActivatedRoute} from "@angular/router";

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

  destroy$: Subject<boolean> = new Subject<boolean>();

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

    this.resources$ = this.route.paramMap.pipe(
      switchMap(params => {
        this.selectedId = Number(params.get('id'));
        this.setCollapsed();
        return this.appService.getResources();
      })
    )
  }
}
