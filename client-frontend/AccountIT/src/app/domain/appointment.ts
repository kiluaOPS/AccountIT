import { Clinic } from "./clinic";
import { Customer } from "./customer";
import { Injury } from './injury';
export class Appointment {
  id: number;
  customer: Customer;
  date: Date;
  type: string;
//   clinic: Clinic;
  injury: Injury;
  constructor() {}
}
