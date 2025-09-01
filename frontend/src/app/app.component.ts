import { Component } from '@angular/core';
import { AirQualityService } from './services/air-quality.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: false
})

export class AppComponent {
  title = 'Monitoramento da Qualidade do Ar';
  cidadeInput: string = 'São Paulo';
  airQualityData: any = null;
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
        this.airQualityData = data;
        this.isLoading = false;
        if (data.status === 'error') {
          this.errorMessage = `Não foram encontrados dados para "${this.cidadeInput}". Verifique o nome e tente novamente.`;
          this.airQualityData = null;
        }
      },
      error: (err) => {
        this.errorMessage = 'Ocorreu um erro ao se comunicar com o servidor. Verifique se o backend está rodando.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }
}
