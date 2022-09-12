import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEntityLoginPromptComponent } from './new-entity-login-prompt.component';

describe('NewEntityLoginPromptComponent', () => {
  let component: NewEntityLoginPromptComponent;
  let fixture: ComponentFixture<NewEntityLoginPromptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewEntityLoginPromptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewEntityLoginPromptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
