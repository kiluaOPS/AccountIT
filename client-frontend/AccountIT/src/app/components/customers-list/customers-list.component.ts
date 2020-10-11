import { Customer } from "./../../domain/customer";
import { Router } from "@angular/router";
import { CustomersService } from "./../../service/data/customers.service";
import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-customers-list",
  templateUrl: "./customers-list.component.html",
  styleUrls: ["./customers-list.component.css"]
})
export class CustomersListComponent implements OnInit {
  @Input() customers: Customer[];
  loadAPI: any;
  constructor(private service: CustomersService, private router: Router) {}

  ngOnInit() {
    if (!this.customers) {
      this.service
        .executeGetAllCustomers()
        .subscribe(
          response => this.handleGetAllCustomers(response),
          error => this.handleErrors(error)
        );
    }
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
    console.log(this.customers);

  }

  requestGetCustomerById(id: number) {
    this.router.navigate([`customer/${id}`]);
  }

  requestDeleteCustomerById(id: number, i: number) {
    this.service.executeDeleteCustomerById(id).subscribe();
    this.customers.splice(i, 1);
  }

  handleGetAllCustomers(response) {
    this.customers = response;
    this.loadAPI = new Promise(resolve => {
      this.loadScript();
    });
  }

  handleCustomerById(response) {
    console.log(response);
  }

  handleErrors(response) {
    console.log(response.error.message);
  }

  loadScript() {
    let node = document.createElement("script");
    node.src = "../../../assets/js/demo/datatables-demo.js";
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }
}
