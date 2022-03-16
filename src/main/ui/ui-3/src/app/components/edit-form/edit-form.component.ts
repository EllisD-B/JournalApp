import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Subject, takeUntil} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {AppService} from "../../app.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'edit-form',
    templateUrl: 'edit-form.component.html',
    styleUrls: ['edit-form.component.css']
  }
)

export class editFormComponent {
  constructor(private appService: AppService, private router: Router, private route: ActivatedRoute) {
  }

  @Input() resource: any;
  resourceId: number;

  @Output() submitEditEvent = new EventEmitter();
  @Output() updatedResource: any;

  updateResourceForm = new FormGroup( {
    name: new FormControl(''),
    url: new FormControl(''),
  });

  destroy$: Subject<boolean> = new Subject<boolean>();

  updateResource(resource: any) {
    this.appService.updateResource(resource);
  }

  async onSubmit() {

    //let resource = {id: Number(this.resourceId), name: this.resource.name , url: this.resource.url }

    if(this.updateResourceForm.get('name')?.value) {
      this.resource.name = this.updateResourceForm.get('name')?.value;
    }

    if(this.updateResourceForm.get('url')?.value) {
      this.resource.url = this.updateResourceForm.get('url')?.value;
    }

    this.appService.updateResource(this.resource).pipe(takeUntil(this.destroy$))
      .subscribe(data => {
        console.log('message:::', data);
        this.updatedResource = this.resource;
        this.updateResourceForm.reset();
        this.submitEditEvent.emit('edited');
      })
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe()
  }

  // ngOnInit() {
  //
  //   this.route.queryParams.subscribe(params => {
  //
  //     this.resourceId = params["id"];
  //
  //     // this.resource = this.appService
  //     //   .findResourceById(this.resourceId)
  //     //   .pipe(takeUntil(this.destroy$))
  //     //   .subscribe((resources: any=[]) => {
  //     //     this.resource = resources.data[0];
  //     //     console.log(this.resource);
  //     //   } );
  //
  //   })
  // }
}
