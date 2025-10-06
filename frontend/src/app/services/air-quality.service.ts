import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AirQualityResponse } from '../models/airquality-response.model'
import { HistoricoDTO } from '../models/historico.dto.model';

@Injectable({
  providedIn: 'root'
})
export class AirQualityService{
  private apiUrlBase = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getDataForCity(city: string): Observable<AirQualityResponse> {
    const fullUrl = `${this.apiUrlBase}/air-quality`;
    const params = new HttpParams().set('city', city);

    return this.http.get<AirQualityResponse>(fullUrl, { params });
  }

  public getHistoryForLocationName(cityName: string): Observable<HistoricoDTO> {
    const fullUrl = `${this.apiUrlBase}/air-quality/history`;
    const params = new HttpParams().set('cityName', cityName);
    return this.http.get<HistoricoDTO>(fullUrl, { params });
  }
}


