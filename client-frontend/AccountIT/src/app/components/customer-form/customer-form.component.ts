import { Router } from '@angular/router';
import { CustomersService } from './../../service/data/customers.service';
import { Customer } from './../../domain/customer';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer-form.component.html',
  styleUrls: ['./customer-form.component.css']
})
export class CustomerFormComponent implements OnInit {
  model = new Customer();
  submitted = false;
  constructor(private service: CustomersService, private router: Router) {}
  invalid = false;

  ngOnInit() {}

  onSubmit() {
    const customer: Customer = this.model;
    console.log(customer.dob);
    this.service
      .executeCreateCustomer(customer)
      .subscribe(
        response => this.handleCustomerCreated(response),
        error => this.handleErrors(error)
      );
  }

  handleCustomerCreated(response) {
    console.log(response);
    this.invalid = false;
    window.location.reload();
  }

  handleErrors(response) {
    console.log(response);
    this.invalid = true;
  }

}
