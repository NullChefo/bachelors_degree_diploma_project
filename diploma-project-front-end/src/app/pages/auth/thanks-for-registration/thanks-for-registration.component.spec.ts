import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ThanksForRegistrationComponent} from './thanks-for-registration.component';

describe('ThanksForRegistrationComponent', () => {
  let component: ThanksForRegistrationComponent;
  let fixture: ComponentFixture<ThanksForRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ThanksForRegistrationComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ThanksForRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
