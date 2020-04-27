document.addEventListener("DOMContentLoaded", () => {
    const productListElements = document.getElementsByClassName("productAddToCart");

	for (let i = 0; i < productListElements.length; i++) {
        productListElements[i].addEventListener("click", productClick);
    }
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

function productClick(event) {
    let listItem = findClickedListItemElement(event.target);

    const saveActionElement = event.target;
    const saveActionUrl = ("/transactionDetail/createTransactionEntry/"); //TODO: WRONG URL
    const saveTransactionEntryRequest = {
        id: "",
        transactionId: "", //TODO: Verify these values
        productId: listItem.querySelector("input[name='productId'][type='hidden']").value,
        quantity: listItem.getElementsByClassName("productQuantity").value,
        price: listItem.getElementsByClassName(productPriceDisplay).value
    };

    ajaxPost(saveActionUrl, saveTransactionEntryRequest), (callbackResponse) => {
        saveActionElement.disabled = false;

        if(isSuccessResponse(callbackResponse))
        {
            completeSaveAction(callbackResponse);
        }
    }


	//window.location.assign(
	//	"/transactionDetail/createTrasactionEntry/"
	//	+ listItem.querySelector("input[name='productId'][type='hidden']").value);
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
