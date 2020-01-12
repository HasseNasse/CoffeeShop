import {Injectable, NgZone} from '@angular/core';
import {SseService} from './sse.service';
import {Observable} from 'rxjs';
import {Order} from '../model/Order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url = 'http://localhost:8070/orders/stream';

  constructor(private _zone: NgZone, private _sseService: SseService) { }

  /**
   * Return an event source stream
   */
  getOrderStream(): Observable<Order> {
    return new Observable(subscriber => {
      const eventSource = this._sseService.getEventSource(this.url);

      eventSource.onmessage = event => {
        this._zone.run(() => {
          subscriber.next(JSON.parse(event.data) as Order);
        });
      };

      eventSource.onerror = error => {
        this._zone.run(() => {
          subscriber.error(error);
        });
      };
    });
  }

}
