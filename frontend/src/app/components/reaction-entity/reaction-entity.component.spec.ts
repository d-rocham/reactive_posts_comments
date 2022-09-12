import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReactionEntityComponent } from './reaction-entity.component';

describe('ReactionEntityComponent', () => {
  let component: ReactionEntityComponent;
  let fixture: ComponentFixture<ReactionEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReactionEntityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReactionEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
