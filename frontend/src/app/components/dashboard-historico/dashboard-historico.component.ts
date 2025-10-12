import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AirQualityService } from '../../services/air-quality.service';
import { HistoricoDTO } from '../../models/historico.dto.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard-historico',
  standalone: true,
  imports: [CommonModule, NgxChartsModule],
  templateUrl: './dashboard-historico.component.html',
  styleUrls: ['./dashboard-historico.component.scss']
})
export class DashboardHistoricoComponent implements OnInit, OnDestroy {
  @Input() locationName!: string;

  private historySubscription?: Subscription;

  historicoData: HistoricoDTO | null = null;
  chartData: any[] = [];
  isLoading = false;
  poluentePredominantePeriodo: string | null = null;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Data';
  yAxisLabel = 'IQAr';
  colorScheme: any = { domain: ['#007bff'] };

  constructor(private airQualityService: AirQualityService) {}

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {
    this.isLoading = true;
    this.historySubscription?.unsubscribe();

    this.historySubscription = this.airQualityService.getHistoryForLocationName(this.locationName).subscribe(data => {
      this.historicoData = data;
      this.calcularPoluentePredominantePeriodo();
      this.formatDataForChart();
      this.isLoading = false;
    });
  }

  ngOnDestroy(): void {
    this.historySubscription?.unsubscribe();
  }

  formatDataForChart(): void {
    if (this.historicoData && this.historicoData.historicoDiario) {
      const series = this.historicoData.historicoDiario.map(item => ({
        name: new Date(item.data),
        value: item.valorAqi,
        extra: { poluente: item.nomePoluente }
      }));
      this.chartData = [{ name: 'IQAr', series: series }];
    }
  }

  calcularPoluentePredominantePeriodo(): void {
    if (!this.historicoData || !this.historicoData.historicoDiario || this.historicoData.historicoDiario.length === 0) {
      this.poluentePredominantePeriodo = 'N/D';
      return;
    }
    const contagem = this.historicoData.historicoDiario
      .map(item => item.nomePoluente)
      .reduce((acc, poluente) => {
        if (poluente) acc[poluente] = (acc[poluente] || 0) + 1;
        return acc;
      }, {} as Record<string, number>);
    this.poluentePredominantePeriodo = Object.keys(contagem).reduce((a, b) => contagem[a] > contagem[b] ? a : b, 'N/D');
  }
}
