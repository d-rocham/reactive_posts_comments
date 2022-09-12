import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityContainerComponent } from './entity-container.component';

describe('EntityContainerComponent', () => {
  let component: EntityContainerComponent;
  let fixture: ComponentFixture<EntityContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EntityContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntityContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
