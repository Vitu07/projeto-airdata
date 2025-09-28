export interface AirQualityResponse {
  dadosApiExterna: {
    aqi: number;
    city: { name: string };
    time: { s: string };
    dominentpol: string;
  };
  classificacaoRisco: string;
  recomendacoes: string[];
}
