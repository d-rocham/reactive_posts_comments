import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEntityFormComponent } from './new-entity-form.component';

describe('NewEntityFormComponent', () => {
  let component: NewEntityFormComponent;
  let fixture: ComponentFixture<NewEntityFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewEntityFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewEntityFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
