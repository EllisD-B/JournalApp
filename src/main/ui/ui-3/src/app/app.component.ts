import {Component, Input, OnDestroy} from '@angular/core';
import {AppService} from "./app.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Subject, takeUntil} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnDestroy {

  constructor(private appService: AppService) {}

  title = 'journalUi';

  public isCollapsed = false;

  resourceForm = new FormGroup( {
    name: new FormControl('', Validators.required),
    url: new FormControl('', Validators.required),
  });
  //
  resources: Array<any> = [];
  currentResource: any;
  resourcesCount = 0;
  public refresh = false;
  destroy$: Subject<boolean> = new Subject<boolean>();
  //
  onSubmit() {
    // this.appService.addResource(this.resourceForm.value).pipe(takeUntil(this.destroy$)).subscribe(data => {
    //   console.log('message::::', data);
    //   this.resourcesCount = this.resourcesCount + 1;
    //   console.log(this.resourcesCount);
    //   this.resourceForm.reset();
    //   this.getAllResources();
    // })
  }
  //
  // onClickDelete(resource: any) {
  //   console.log(resource);
  //   console.log("ID: " + resource.id);
  //   this.appService.removeResource(resource).pipe(takeUntil(this.destroy$)).subscribe(data => {
  //     console.log('message:::', data);
  //     this.resourcesCount = this.resourcesCount-1;
  //     this.getAllResources();
  //   })
  // }
  //
  getAllResources() {
    // @ts-ignore
    // this.appService.getResources()
    //   .pipe(takeUntil(this.destroy$))
    //   .subscribe(
    //     (resources: any=[]) => {
    //       this.resourcesCount = resources.data.length;
    //       this.resources = resources.data;
    //       console.log(resources)
    //     }
    //   )
  }
  //
  // setCurrentResource(resource: any) {
  //   this.appService.setCurrentResource(resource);
  // }
  //
  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe()
  }
  //
  // goToEdit(resource: any) {
  //   this.router.navigate(['edit-page'], {queryParams: { id : resource.id }});
  // }
  //
  // ngOnInit() {
  //   //this.getAllResources();
  //
  //   // this.resources$ = this.route.paramMap.pipe(
  //   //   switchMap(params => {
  //   //     this.selectedId = Number(params.get('id'));
  //   //     return this.appService.getResources();
  //   //   })
  //   // )
  //
  // }
}
