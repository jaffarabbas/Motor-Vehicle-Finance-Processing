$(document).ready(function() {
    $("#product").change(function() {
        var course_id = $(this).val();
        $.ajax({
            url: "action.php",
            method: "POST",
            data: { courseID: course_id },
            success: function(data) {
                $("#product_Type").html(data);
            }
        });
    });
});

$(document).ready(function() {
    $('#myTable').DataTable();
});


////////////////////////////////
//order view script

   
// function dashborad_function() {
//     var dashbord_view = document.getElementById("dashboard");
//     var order_view = document.getElementById("sec1");
//     var tablepage_view = document.getElementById("sec2");
//     var defaulter_view = document.getElementById("sec3");
//     var testedproduct_view = document.getElementById('sec4');
//     if (dashbord_view.style.display === "none") {
//         dashbord_view.style.display = "block";
//         order_view.style.display = "none";
//         tablepage_view.style.display = "none";
//         defaulter_view.style.display = "none";
//         testedproduct_view.style.display = "none";
//     } else {
//         dashbord_view.style.display = "none";
//     }
// }    
// function orderview_function() {
//     var order_view = document.getElementById("sec1");
//     var dashbord_view = document.getElementById("dashboard");
//      var tablepage_view = document.getElementById("sec2");
//      var defaulter_view = document.getElementById("sec3");
//      var testedproduct_view = document.getElementById('sec4');
//     if (order_view.style.display === "none") {
//         order_view.style.display = "block";
//         dashbord_view.style.display = "none";
//         tablepage_view.style.display = "none";
//         defaulter_view.style.display = "none";
//         testedproduct_view.style.display = "none";
//     } else {
//         order_view.style.display = "none";
//     }
// }
// function testpage_view_function() {
//     var tablepage_view = document.getElementById("sec2");
//     var order_view = document.getElementById("sec1");
//     var defaulter_view = document.getElementById("sec3");
//     var dashbord_view = document.getElementById("dashboard");
//     var testedproduct_view = document.getElementById('sec4');
//     if (tablepage_view.style.display === "none") {
//         tablepage_view.style.display = "block";
//         order_view.style.display = "none";
//         dashbord_view.style.display = "none";
//         defaulter_view.style.display = "none";
//         testedproduct_view.style.display = "none";
//     } else {
//         tablepage_view.style.display = "none";
//     }
// }

// function defaulter_view_function() {
//     var defaulter_view = document.getElementById('sec3');
//     var tablepage_view = document.getElementById("sec2");
//     var order_view = document.getElementById("sec1");
//     var dashbord_view = document.getElementById("dashboard");
//     var testedproduct_view = document.getElementById('sec4');
//     if (defaulter_view.style.display === "none") {
//         defaulter_view.style.display = "block";
//         tablepage_view.style.display = "none";
//         order_view.style.display = "none";
//         dashbord_view.style.display = "none";
//         testedproduct_view.style.display = "none";
//     } else {
//         defaulter_view.style.display = "none";
//     }
// }


// function testedproduct_view_function() {
//     var testedproduct_view = document.getElementById('sec4');
//     var defaulter_view = document.getElementById('sec3');
//     var tablepage_view = document.getElementById("sec2");
//     var order_view = document.getElementById("sec1");
//     var dashbord_view = document.getElementById("dashboard");
//     if (testedproduct_view.style.display === "none") {
//         testedproduct_view.style.display = "block";
//         tablepage_view.style.display = "none";
//         order_view.style.display = "none";
//         dashbord_view.style.display = "none";
//         defaulter_view.style.display = "none";
//     } else {
//         testedproduct_view.style.display = "none";
//     }
// }


  
$('#dashboard').hide();
$('#togle_dashboard').on('click',
  function() {
    $('#dashboard,#dashboard').toggle(200);
  }
);   

// $('.test_product_view').hide();
// $('.nav-link').on('click',
//   function() {
//     $('.order_view,.dashboard').toggle(200);
//   }
// );

