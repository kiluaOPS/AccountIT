function deleteAppointment(id) {
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