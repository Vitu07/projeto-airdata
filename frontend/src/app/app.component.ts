import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AirQualityService } from './services/air-quality.service';
import { AirQualityResponse } from './models/airquality-response.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    FormsModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Monitoramento da Qualidade do Ar';
  cidadeInput: string = 'São Paulo';
  airQualityData: AirQualityResponse | null = null;
  isLoading: boolean = false;
  errorMessage: string | null = null;

  constructor(private airQualityService: AirQualityService) {}

  buscarQualidadeDoAr() {
    if (!this.cidadeInput) {
      alert('Por favor, digite o nome de uma cidade.');
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;
    this.airQualityData = null;

    const cidadeFormatada = this.cidadeInput
      .trim()
      .toLowerCase()
      .replace(/ /g, '-')
      .normalize('NFD').replace(/[\u0300-\u036f]/g, '');

    this.airQualityService.getDataForCity(cidadeFormatada).subscribe({
      next: (data) => {
        this.isLoading = false;
        if (data && data.classificacaoRisco) {
          this.airQualityData = data;
          this.errorMessage = null;
        } else {
          // Se não, exibe uma mensagem de erro amigável
          this.airQualityData = null;
          this.errorMessage = `Não foram encontrados dados para "${this.cidadeInput}". Verifique o nome e tente novamente.`;
        }
      },
      error: (err) => {
        this.errorMessage = 'Ocorreu um erro ao se comunicar com o servidor. Verifique se o backend está rodando.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  formatCssClass(classification: string): string {
    if (!classification) {
      return '';
    }
    const formattedClass = classification.toLowerCase().replace(/ /g, '-');
    return 'risco-' + formattedClass;
  }
}
