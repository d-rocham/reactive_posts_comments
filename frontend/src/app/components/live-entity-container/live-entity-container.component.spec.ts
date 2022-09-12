import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEntityContainerComponent } from './live-entity-container.component';

describe('LiveEntityContainerComponent', () => {
  let component: LiveEntityContainerComponent;
  let fixture: ComponentFixture<LiveEntityContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LiveEntityContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LiveEntityContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
