export interface Order {
  id: string;
  orderer: string;
  quantity: number;
  type: string;
  created: Date;
  modified: Date;
  status: string;
}
