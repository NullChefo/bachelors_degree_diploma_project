import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LikeItemComponent} from './like-item.component';

describe('LikeItemComponent', () => {
  let component: LikeItemComponent;
  let fixture: ComponentFixture<LikeItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LikeItemComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LikeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
