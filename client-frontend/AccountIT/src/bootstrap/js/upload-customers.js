'use strict';

var customerForm = document.querySelector('#customerForm');
var customerInput = document.querySelector('#customerInput');
var customerError = document.querySelector('#customerError');
var customerSuccess = document.querySelector('#customerSuccess');

var divForm = document.querySelector('.uploadDiv')
var table = document.querySelector(".table");

/********************
 *Customers upload
 */

function uploadCustomersFile(file) {
  var formData = new FormData();
  formData.append("file", file);

  $.ajax({
    url: '/upload/customers',
    type: 'POST',
    data: formData,
    cache: false,
    contentType: false,
    enctype: 'multipart/form-data',
    processData: false,
    success: function(data) {
      customerError.style.display = "none";
      generateCustomersTable(data);
      console.log(data);
      customerSuccess.style.display = "block";
      divForm.style.display = "none";
    },
    error: function(data) {
      customerSuccess.style.display = "none";
      customerError.innerHTML = (data && data.message) || "Some Error Occurred";
    }
  });
}

customerForm.addEventListener('submit', function(event) {
  var files = customerInput.files;
  if (files.length === 0) {
    customerError.innerHTML = "Please select a file";
    customerError.style.display = "block";
  }
  console.log(table)
  uploadCustomersFile(files[0]);
  event.preventDefault();
}, true);

/*************************
 *Visualize a CustomerTable
 */

function generateCustomersTable(data) {
  generateTableHead(data[0]);
  let tbody = table.createTBody();
  let count = 0;
  for (let element of data) {
    var id;
    let row = tbody.insertRow();
    for (let key in element) {
      if (key == "id") {
        id = element[key]
      }
      let cell = row.insertCell();
      let text = document.createTextNode(element[key]);
      cell.appendChild(text);
    }
    let cell = row.insertCell();
    let input = document.createElement("button");
    input.setAttribute("class", "delete-button");
    input.setAttribute("id", id);
    let icon = document.createElement("i");
    icon.setAttribute("class", "fa fa-trash");
    input.setAttribute("onclick", "deleteCustomer(" + id + ")");
    input.appendChild(icon);
    cell.appendChild(input);
  }
}

function deleteCustomer(id) {
  var row = $("#" + id + "");
  $.ajax({

    url: "/customers/delete/" + id,

    type: 'DELETE',

    contentType: 'application/json',

    success: function(data) {
      $("#" + id).closest("tr").remove();
    }
  });
}





//
//function getDataFromTable() {
//    var myRows = [];
//    var headersText = [];
//    var $headers = $("th");
//
//    // Loop through grabbing everything
//    var $rows = $("tbody tr").each(function(index) {
//        var $cells = $(this).find("td");
//        myRows[index] = {};
//
//        $cells.each(function(cellIndex) {
//        // Set the header text
//        if(headersText[cellIndex] === undefined) {
//          headersText[cellIndex] = $($headers[cellIndex]).text();
//        }
//        // Update the row object with the header/cell combo
//        myRows[index][headersText[cellIndex]] = $(this).text();
//        });
//    });
//
//    for (let i = 0; i < myRows.length; i++) {
//        delete myRows[i][""];
//    }
//    return myRows;
//}
//
//function saveCustomers() {
//    var customersList = getDataFromTable();
//    var customers= [];
//    for (let i = 0; i < customersList.length; i++) {
//        customers.push(customersList[i]);
//    }
//    customers = JSON.stringify({
//                'customers' : customers
//            });
//
//     $.ajax({
//
//        url:"/addCustomers",
//
//        type: 'POST',
//
//        data:  customers,
//
//        dataType: 'html',
//
//        contentType: 'application/json',
//
//        success: function(data){
//
//            console.log(data);
//            $("#save-button").hide();
//            $(".delete-button").hide();
//        }
//     });
//}
//
//function saveAppointments() {
//    var appointmentsList = getDataFromTable();
//    var appointments= [];
//    for (let i = 0; i < appointmentsList.length; i++) {
//        appointments.push(appointmentsList[i]);
//    }
//    appointments = JSON.stringify({
//                'appointments' : appointments
//            });
//
//     $.ajax({
//
//        url:"/addAppointments",
//
//        type: 'POST',
//
//        data:  appointments,
//
//        dataType: 'html',
//
//        contentType: 'application/json',
//
//        success: function(data){
//            $("#save-button").hide();
//            $(".delete-button").hide();
//        }
//     });
//}