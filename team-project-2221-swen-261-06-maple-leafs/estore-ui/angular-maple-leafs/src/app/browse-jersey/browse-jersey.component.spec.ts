import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseJerseyComponent } from './browse-jersey.component';

describe('BrowseJerseyComponent', () => {
  let component: BrowseJerseyComponent;
  let fixture: ComponentFixture<BrowseJerseyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseJerseyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseJerseyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
