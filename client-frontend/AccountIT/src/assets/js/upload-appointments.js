'use strict';

var appointForm = document.querySelector('#appointForm');
var appointInput = document.querySelector('#appointInput');
var appointError = document.querySelector('#appointError');
var appointSuccess = document.querySelector('#appointSuccess');
var divForm = document.querySelector('.uploadDiv')
var table = document.querySelector(".table");


/********************
 *Appointments upload
 */

function uploadAppointmentsFile(file) {
  var formData = new FormData();
  formData.append("file", file);

  $.ajax({
    url: '/upload/appointments',
    type: 'POST',
    data: formData,
    cache: false,
    contentType: false,
    enctype: 'multipart/form-data',
    processData: false,
    success: function(data) {
      appointError.style.display = "none";
      generateTable(data);
      customerSuccess.style.display = "block";
      divForm.style.display = "none";
    },
    error: function(data) {
      appointSuccess.style.display = "none";
      appointError.innerHTML = (response && response.message) || "Some Error Occurred";
    }
  });
}

appointForm.addEventListener('submit', function(event) {
  var files = appointInput.files;
  if (files.length === 0) {
    appointError.innerHTML = "Please select a file";
    appointError.style.display = "block";
  }
  uploadAppointmentsFile(files[0]);
  event.preventDefault();
}, true);

/*************************
 *Visualize a AppointmentsTable
 */

function generateTableHead(data) {
  let thead = table.createTHead();
  let row = thead.insertRow();
  for (let key in data) {
    let th = document.createElement("th");
    let text = document.createTextNode(key);
    th.appendChild(text);
    row.appendChild(th);
  }
}

function generateTable(data, type) {
  generateTableHead(data[0]);
  let tbody = table.createTBody();
  let count = 0;
  for (let element of data) {
    var id;
    let row = tbody.insertRow();
    for (let key in element) {
      if (key == "appointmentId") {
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
    input.setAttribute("onclick", "deleteRow(" + id + ")");
    input.appendChild(icon);
    cell.appendChild(input);
  }
}

function deleteRow(id) {
  var row = $("#" + id + "");
  $.ajax({

    url: "/appointments/delete/" + id,

    type: 'DELETE',

    contentType: 'application/json',

    success: function(data) {
      $("#" + id).closest("tr").remove();

    },
  });
}



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