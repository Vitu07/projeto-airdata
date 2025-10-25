import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AirQualityService } from './services/air-quality.service';
import { AirQualityResponse } from './models/airquality-response.model';
import { DashboardHistoricoComponent } from './components/dashboard-historico/dashboard-historico.component';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ CommonModule, RouterOutlet, FormsModule, DashboardHistoricoComponent ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  cidadeInput: string = 'São Paulo';
  cidadePesquisada: string | null = null;
  airQualityData: AirQualityResponse | null = null;
  isLoading: boolean = false;
  errorMessage: string | null = null;

  private blockedCities = new Set([
    'taboao da serra',
    'itaquaquecetuba',
    'barueri',
    'santo andre',
    'ribeirao pires',
    'embu das artes',
    'itapecerica da serra',
    'diadema',
    'poa',
    'piedade'
  ]);

  constructor(private airQualityService: AirQualityService) {}

  buscarQualidadeDoAr() {
    if (!this.cidadeInput) {
      alert('Por favor, digite o nome de uma cidade.');
      return;
    }

    const cidadeFormatada = this.cidadeInput.trim().toLowerCase()
      .normalize("NFD").replace(/[\u0300-\u036f]/g, '');

    if (this.blockedCities.has(cidadeFormatada)) {
      this.errorMessage = `A localização "${this.cidadeInput}" não é suportada no momento.`;
      this.airQualityData = null;
      this.cidadePesquisada = null;
      this.isLoading = false;
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;
    this.airQualityData = null;
    this.cidadePesquisada = null;

    this.airQualityService.getDataForCity(cidadeFormatada)
      .pipe(
        finalize(() => this.isLoading = false)
      )
      .subscribe({
      next: (data) => {
        this.isLoading = false;
        if (data && data.classificacaoRisco) {
          this.airQualityData = data;
          this.cidadePesquisada = cidadeFormatada;
        } else {
          this.errorMessage = `Não foram encontrados dados para "${this.cidadeInput}". Verifique o nome e tente novamente.`;
        }
      },
      error: (err) => {
        this.errorMessage = 'Ocorreu um erro ao se comunicar com o servidor.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  formatCssClass(classification: string): string {
    if (!classification) return '';
    return 'risco-' + classification.toLowerCase().replace(/ /g, '-');
  }
}
