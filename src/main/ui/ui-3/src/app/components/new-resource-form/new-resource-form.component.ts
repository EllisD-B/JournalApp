import {Component, EventEmitter, Output} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../app.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'new-resource-form',
  templateUrl: 'new-resource-form.component.html',
  styleUrls: ['new-resource-form.component.css']
  }
)

export class newResourceFormComponent {
  constructor(private appService: AppService) {
  }

 @Output() getAllResourcesEvent = new EventEmitter;

  resourceForm = new FormGroup( {
    name: new FormControl('', Validators.required),
    url: new FormControl('', Validators.required),
  });

  destroy$: Subject<boolean> = new Subject<boolean>();

  onSubmit() {
    this.appService.addResource(this.resourceForm.value).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message::::', data);
      this.resourceForm.reset();
      this.getAllResourcesEvent.emit('get all resources')
    })
  }
}
