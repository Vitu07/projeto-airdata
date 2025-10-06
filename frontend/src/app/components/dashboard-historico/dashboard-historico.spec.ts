import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardHistoricoComponent } from './dashboard-historico.component';

describe('DashboardHistorico', () => {
  let component: DashboardHistoricoComponent;
  let fixture: ComponentFixture<DashboardHistoricoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardHistoricoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardHistoricoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
