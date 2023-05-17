import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private dataSubject = new BehaviorSubject<any>({});
  constructor() { }

  setData(data: any) {
    this.dataSubject.next(data);
  }

  getData() {
    return this.dataSubject.asObservable();
  }
}
