import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {Subject, takeUntil} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {AppService} from "../../app.service";
import {ActivatedRoute, Router} from "@angular/router";

interface SelectItem {
  id: number;
  tag: string;
}

@Component({
    selector: 'edit-form',
    templateUrl: 'edit-form.component.html',
    styleUrls: ['edit-form.component.css']
  }
)

export class editFormComponent implements OnInit {
  constructor(private appService: AppService, private router: Router, private route: ActivatedRoute) {
  }

  @Input() resource: any;
  resourceId: number;

  @Output() submitEditEvent = new EventEmitter();
  @Output() updatedResource: any;

  dropdownList = Array<SelectItem>();

  selectedItems = Array<SelectItem>();

  dropdownSettings = {};

  updateResourceForm = new FormGroup( {
    name: new FormControl(''),
    url: new FormControl(''),
    tags: new FormControl('')
  });

  destroy$: Subject<boolean> = new Subject<boolean>();

  updateResource(resource: any) {
    this.appService.updateResource(resource);
  }

  async onSubmit() {

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
