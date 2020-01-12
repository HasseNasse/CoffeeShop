import {Component, OnDestroy, OnInit} from '@angular/core';
import {OrderService} from '../../core/http/order.service';
import {Order} from '../../core/model/Order';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit, OnDestroy {
  private sseStream: Subscription;
  orders: Array<Order> = [];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.sseStream = this.orderService.getOrderStream().subscribe(orderData => {
      console.log(this.orders);
      this.orders.push(orderData);
    });
  }

  ngOnDestroy(): void {
    if (this.sseStream) {
      this.sseStream.unsubscribe();
    }
  }

  customTB(index, order) {
    return `${index}-${order.id}`;
  }
}
