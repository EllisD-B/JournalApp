import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../app.service";
import {Subject, takeUntil} from "rxjs";


interface SelectItem {
  id: number;
  tag: string;
}

@Component({
  selector: 'new-resource-form',
  templateUrl: 'new-resource-form.component.html',
  styleUrls: ['new-resource-form.component.css'],
  encapsulation: ViewEncapsulation.None
  }
)

export class newResourceFormComponent implements OnInit {
  constructor(private appService: AppService) {
  }

 @Output() getAllResourcesEvent = new EventEmitter;

  dropdownList = Array<SelectItem>();

  selectedItems = Array<SelectItem>();

  dropdownSettings = {};

  resourceForm = new FormGroup( {
    name: new FormControl('', Validators.required),
    url: new FormControl('', Validators.required),
    tags: new FormControl('')
  });

  destroy$: Subject<boolean> = new Subject<boolean>();

  onSubmit() {
    console.log(this.resourceForm.get('tags')?.value);
    let tagString = '';
    for(let i = 0; i < this.resourceForm.get('tags')?.value.length; i++) {
      tagString = tagString.concat(this.resourceForm.get('tags')?.value[i].tag + ', ');
      console.log(tagString)
    }
    let toUpdate = { name: this.resourceForm.get('name')?.value, url: this.resourceForm.get('url')?.value, tags: tagString};
    console.log(toUpdate)
    this.appService.addResource(toUpdate).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message::::', data);
      this.resourceForm.reset();
      this.getAllResourcesEvent.emit('get all resources')
    })
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
    this.selectedItems = [
      { id: 1, tag: 'Java' },
      { id: 6, tag: 'Testing' }
    ];
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
