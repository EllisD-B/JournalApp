import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})

export class navbarComponent {
  constructor() {
  }

  @Output() submitCreateResourceEvent = new EventEmitter();

  submitEvent() {
    this.submitCreateResourceEvent.emit('create resource')
  }
  isCollapsed = true;
}
