import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchJerseysComponent } from './search-jerseys.component';

describe('SearchJerseysComponent', () => {
  let component: SearchJerseysComponent;
  let fixture: ComponentFixture<SearchJerseysComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchJerseysComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchJerseysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
