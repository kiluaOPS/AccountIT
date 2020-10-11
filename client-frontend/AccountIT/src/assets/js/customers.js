function deleteCustomer(id) {
  var row = $("#" + id + "");

  $.ajax({

    url: "/customers/delete/" + id,

    type: 'DELETE',

    contentType: 'application/json',

    success: function(data) {
      $("#" + id).closest("tr").remove();

    },
  });
}