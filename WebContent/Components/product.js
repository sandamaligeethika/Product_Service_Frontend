$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
{
$("#alertSuccess").hide();
}
$("#alertError").hide();
});

//SAVE ======================================================
$(document).on("click", "#btnSave", function(event)
{
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

		// Form validation-------------------
		var status = validateProductForm();
		if (status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}

		// If valid------------------------
		var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
		$.ajax(
		{
		url : "ProductAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
		onProductSaveComplete(response.responseText, status);
		}
		});

		});



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event){
	$("#hidItemIDSave").val($(this).data("itemid"));
	$("#productId").val($(this).closest("tr").find('td:eq(0)').text());
	$("#productName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#productDate").val($(this).closest("tr").find('td:eq(2)').text());
	$("#productPrice").val($(this).closest("tr").find('td:eq(3)').text());
	$("#researcherId").val($(this).closest("tr").find('td:eq(4)').text());
});

// DELETE==========================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
	url : "ProductAPI",
	type : "DELETE",
	//change itemID to id
	data : "id=" + $(this).data("itemid"),
	dataType : "text",
	complete : function(response, status)
	{
	onProductDeleteComplete(response.responseText, status);
	}
	});
});

// CLIENT-MODEL================================================================
function validateProductForm()
{
// CODE
if ($("#productId").val().trim() == "")
{
return "Insert Product Id.";
}
// NAME
if ($("#productName").val().trim() == "")
{
return "Insert Product Name.";
}
//DATE-------------------------------
if ($("#productDate").val().trim() == "")
{
return "Insert Date";
}
// PRICE-------------------------------
if ($("#productPrice").val().trim() == "")
{
return "Insert Price.";
}
// is numerical value
var tmpPrice = $("#productPrice").val().trim();
if (!$.isNumeric(tmpPrice))
{
return "Insert a numerical value for Item Price.";
}
// convert to decimal price
$("#productPrice").val(parseFloat(tmpPrice).toFixed(2));
// DESCRIPTION------------------------
if ($("#researcherId").val().trim() == "")
{
return "Insert Researcher Id.";
}
return true;
}



function onProductSaveComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);
if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully saved.");
$("#alertSuccess").show();
$("#divItemsGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while saving.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while saving..");
$("#alertError").show();
}

$("#hidItemIDSave").val("");
$("#formItem")[0].reset();
}



function onProductDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
		$("#alertSuccess").text("Successfully deleted.");
		$("#alertSuccess").show();
		$("#divItemsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}