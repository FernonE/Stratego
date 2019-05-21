import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeelstukComponent } from './speelstuk.component';

describe('SpeelstukComponent', () => {
  let component: SpeelstukComponent;
  let fixture: ComponentFixture<SpeelstukComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpeelstukComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpeelstukComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
