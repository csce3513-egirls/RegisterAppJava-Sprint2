document.addEventListener("DOMContentLoaded", () => {
    const productListElements = document.getElementById("transactionListing").children;
    const finishTransactionButton = document.getElementById("checkOutButton");
    const cancelTransactionButton = document.getElementById("cancelButton");

	for (let i = 0; i < productListElements.length; i++) {
    productListElements[i].addEventListener("click", productClick);
    }
    
    finishTransactionButton.addEventListener("click", finishTransactionClick);

    cancelTransactionButton.addEventListener("click", cancelTransactionClick);


});

function findClickedListItemElement(clickedTarget) {
	if (clickedTarget.tagName.toLowerCase() === "li") {
		return clickedTarget;
	} else {
		let ancestorIsListItem = false;
		let ancestorElement = clickedTarget.parentElement;

		while (!ancestorIsListItem && (ancestorElement != null)) {
			ancestorIsListItem = (ancestorElement.tagName.toLowerCase() === "li");

			if (!ancestorIsListItem) {
				ancestorElement = ancestorElement.parentElement;
			}
		}

		return (ancestorIsListItem ? ancestorElement : null);
	}
}

function completeSaveAction(callbackResponse) {
	if (callbackResponse.data == null) {
		return;
	}

	if ((callbackResponse.data.redirectUrl != null)
		&& (callbackResponse.data.redirectUrl !== "")) {

		window.location.replace(callbackResponse.data.redirectUrl);
		return;
	}
}

function finishTransactionClick(event) {
    let finishTransactionElement = event.target;
    
    const finishActionUrl = ("api/transaction/finishTransaction");
    const finishActionRequest = null;

    ajaxPost(finishActionUrl, finishActionRequest, (callbackResponse) => {
        finishTransactionElement.disabled = false;

        if (isSuccessResponse(callbackResponse)) {

            if ((callbackResponse.data != null)
                && (callbackResponse.data.id != null)
                && (callbackResponse.data.id.trim() !== "")) {

            }
        }
    });
}

function cancelTransactionClick(event) {
    let cancelTransactionElement = event.target;
    
    const finishActionUrl = ("api/transaction/cancelTransaction");

    ajaxDelete(finishActionUrl, (callbackResponse) => {
        finishTransactionElement.disabled = false;

        if (isSuccessResponse(callbackResponse)) {

            if ((callbackResponse.data != null)
                && (callbackResponse.data.id != null)
                && (callbackResponse.data.id.trim() !== "")) {

            }
        }
    });
}


function productClick(event) {
	let listItem = findClickedListItemElement(event.target);

	window.location.assign(
		"/productDetail/"
		+ listItem.querySelector("input[name='productId'][type='hidden']").value);
}

//partial search 
function search() {
    document.getElementById("dropdown").classList.toggle("show");
}
  
function filterInput() {
    var input, filter, a, i;
    input = document.getElementById("userInput");
    filter = input.value.toUpperCase();
    div = document.getElementById("dropdown");
    a = div.getElementsByTagName("li");
    for (i = 0; i < a.length; i++) {
      txtValue = a[i].textContent || a[i].innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        a[i].style.display = "";
      } else {
        a[i].style.display = "none";
      }
    }
  }

function findTotals() {

}
