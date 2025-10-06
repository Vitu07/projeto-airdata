import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AirQualityService } from '../../services/air-quality.service';
import { HistoricoDTO } from '../../models/historico.dto.model';

@Component({
  selector: 'app-dashboard-historico',
  standalone: true,
  imports: [CommonModule, NgxChartsModule],
  templateUrl: './dashboard-historico.component.html',
  styleUrls: ['./dashboard-historico.component.scss']
})
export class DashboardHistoricoComponent implements OnChanges {
  @Input() locationName: string | null = null;

  historicoData: HistoricoDTO | null = null;
  chartData: any[] = [];
  isLoading = false;

  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Data';
  yAxisLabel = 'IQAr';
  colorScheme: any = {
    domain: ['#007bff']
  };

  constructor(private airQualityService: AirQualityService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['locationName'] && this.locationName) {
      this.loadHistory();
    }
  }

  loadHistory(): void {
    if (!this.locationName) return;
    this.isLoading = true;

    this.airQualityService.getHistoryForLocationName(this.locationName).subscribe(data => {
      this.historicoData = data;
      this.formatDataForChart();
      this.isLoading = false;
    });
  }

  formatDataForChart(): void {
    if (this.historicoData && this.historicoData.historicoDiario) {
      const series = this.historicoData.historicoDiario.map(item => {
        return { name: new Date(item.data), value: item.valorAqi };
      });
      this.chartData = [{ name: 'IQAr', series: series }];
    }
  }
}
