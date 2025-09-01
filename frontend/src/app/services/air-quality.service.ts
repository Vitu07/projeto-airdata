import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AirQualityService{
  private apiUrlBase = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getDataForCity(city: string): Observable<any> {
    const fullUrl = `${this.apiUrlBase}/air-quality`;
    const params = new HttpParams().set('city', city);

    return this.http.get<any>(fullUrl, { params });
  }
}
