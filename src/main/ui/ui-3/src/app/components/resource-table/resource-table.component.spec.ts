import { ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {resourceTableComponent} from "./resource-table.component";
import {AppService} from "../../app.service";
import any = jasmine.any;

describe('Resource Table Component', () => {

  let fixture: ComponentFixture<resourceTableComponent>;
  let component: resourceTableComponent;
  let mockAppService;

  beforeEach(async () => {

    const youtube = {id: 1, name: "Youtube", url: "https://youtube.co.uk", tags: "Java, Testing"};
    const codeAcademy = {id: 2, name: "CodeAcademy", url: "https://codeacademy.co.uk", tags: "Python, Databases"};

    //mockAppService = jasmine.createSpyObj(['getResources', 'filterResourcesByTags', 'removeResource']);
    //mockAppService.getResources.and.returnValue([youtube, codeAcademy]);
    // mockAppService.filterResourcesByTags("Java").and.returnValue(youtube);
    // mockAppService.removeResource(youtube).and.returnValue(youtube);

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
      declarations: [
        resourceTableComponent
      ],
      providers: [{ provide: AppService}]
    })


  })

  it('should create the table', () => {
    fixture = TestBed.createComponent(resourceTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.getAllResources();
    const table = fixture.nativeElement as HTMLElement;
    //const filterButton = table.querySelector('#filterButton').textContent;
    console.log('here')
    console.log(table);
    //expect(table.querySelector('.btn .btn-outline-primary')!.textContent).toContain('Filter By Tag')
    //console.log(filterButton)
    expect(table).toBeTruthy();
  })

})
