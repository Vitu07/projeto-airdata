export interface MedicaoDiariaDTO {
  data: string;
  valorAqi: number;
  nomePoluente: string;
}

export interface HistoricoDTO {
  aqiMax: number;
  aqiMin: number;
  aqiAvg: number;
  historicoDiario: MedicaoDiariaDTO[];
  poluentePredominanteDescricao: String;
}
