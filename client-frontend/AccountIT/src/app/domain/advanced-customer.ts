import { AppointmentsList } from "./appointments-list";
import { Customer } from "./customer";
import { Appointment } from "./appointment";
export interface AdvancedCustomer {
  customer: Customer;
  appointmentsSets: AppointmentsList[];
  appointments: Appointment[];
}
