export interface AirQualityResponse {
  dadosApiExterna: {
    aqi: number;
    idx: number;
    city: { name: string };
    time: { s: string };
    dominentpol: string;
  };
  classificacaoRisco: string;
  recomendacoes: string[];
}
