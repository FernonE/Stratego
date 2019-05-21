import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpelerComponent } from './speler.component';

describe('SpelerComponent', () => {
  let component: SpelerComponent;
  let fixture: ComponentFixture<SpelerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpelerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpelerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
