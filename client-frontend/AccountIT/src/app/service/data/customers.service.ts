import { HttpHeaders } from '@angular/common/http';
import { AdvancedCustomer } from 'src/app/domain/advanced-customer';
import { Customer } from './../../domain/customer';
import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  constructor(private http: HttpClient) { }

  executeGetAllCustomers() {
    return this.http.get<Customer[]>('http://localhost:8080/customers');
  }

  executeGetCustomerById(id) {
    return this.http.get<Customer>(`http://localhost:8080/customer/${id}`);
  }

  executeGetAdvCustomerById(id) {
    return this.http.get<AdvancedCustomer>(`http://localhost:8080/statistics/advanced-customer/${id}`);
  }

  executeDeleteCustomerById(id) {
    return this.http.delete(`http://localhost:8080/customers/delete/${id}`);
  }

  executeCustomerChanges(customer: Customer) {
    return this.http.put<Customer>('http://localhost:8080/customer', customer);
  }

  executeCreateCustomer(customer: Customer) {
    let httpHeaders = new HttpHeaders().set("Content-Type", "application/json");
    let options = {
      headers: httpHeaders
    };
    return this.http.post<Customer>(
      "http://localhost:8080/customer",
      customer,
      options
    );
  }

}
