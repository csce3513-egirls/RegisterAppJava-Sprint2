let hideProductSavedAlertTimer = undefined;
let hideProductAddToCartAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	const productLookupCodeElement = getProductLookupCodeElement();

	getProductCountElement().addEventListener("keypress", productCountKeypress);
	getProductPriceElement().addEventListener("keypress", productPriceKeypress);
	productLookupCodeElement.addEventListener("keypress", productLookupCodeKeypress);
	
	getSaveActionElement().addEventListener("click", saveActionClick);
	getDeleteActionElement().addEventListener("click", deleteActionClick);

	getCartActionElement().addEventListener("click", cartActionClick);

	if (!productLookupCodeElement.disabled) {
		productLookupCodeElement.focus();
		productLookupCodeElement.select();
	}
});

function productLookupCodeKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	const productCountElement = getProductCountElement();
	productCountElement.focus();
	productCountElement.select();

	const productPriceElement = getProductPriceElement();
	productPriceElement.focus();
	productPriceElement.select();
}

function productCountKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	saveActionClick();
}

function productPriceKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	saveActionClick();
}

// Save
function saveActionClick(event) {
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const productId = getProductId();
	const productIdIsDefined = ((productId != null) && (productId.trim() !== ""));
	const saveActionUrl = ("/api/product/"
		+ (productIdIsDefined ? productId : ""));
	const saveProductRequest = {
		id: productId,
		count: getProductCount(),
		price: getProductPrice(),
		lookupCode: getProductLookupCode()
	};

	if (productIdIsDefined) {
		ajaxPut(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayProductSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayProductSavedAlertModal();

				if ((callbackResponse.data != null)
					&& (callbackResponse.data.id != null)
					&& (callbackResponse.data.id.trim() !== "")) {

					document.getElementById("deleteActionContainer").classList.remove("hidden");

					setProductId(callbackResponse.data.id.trim());
				}
			}
		});
	}
};

function validateSave() {
	const lookupCode = getProductLookupCode();
	if ((lookupCode == null) || (lookupCode.trim() === "")) {
		displayError("Please provide a valid product lookup code.");
		return false;
	}

	const count = getProductCount();
	if ((count == null) || isNaN(count)) {
		displayError("Please provide a valid product count.");
		return false;
	} else if (count < 0) {
		displayError("Product count may not be negative.");
		return false;
	}

	const price = getProductPrice();
	if ((price == null) || isNaN(price)) {
		displayError("Please provide a valid product price.");
		return false;
	} else if ([price] < 0) {
		displayError("Product price may not be negative.");
		return false;
	}

	return true;
}

function displayProductSavedAlertModal() {
	if (hideProductSavedAlertTimer) {
		clearTimeout(hideProductSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideProductSavedAlertTimer = setTimeout(hideProductSavedAlertModal, 1200);
}

function hideProductSavedAlertModal() {
	if (hideProductSavedAlertTimer) {
		clearTimeout(hideProductSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

// Delete
function deleteActionClick(event) {
	const deleteActionElement = event.target;
	const deleteActionUrl = ("/api/product/" + getProductId());

	deleteActionElement.disabled = true;

	ajaxDelete(deleteActionUrl, (callbackResponse) => {
		deleteActionElement.disabled = false;

		if (isSuccessResponse(callbackResponse)) {
			window.location.replace("/");
		}
	});
};
// End delete
function completeSaveAction(callbackResponse) {//TODO: Function might //be incomplete
	if (callbackResponse.data == null) {
		return;
	}

	if ((callbackResponse.data.redirectUrl != null)
		&& (callbackResponse.data.redirectUrl !== "")) {

		window.location.replace(callbackResponse.data.redirectUrl);
		return;
    }
}
//TODO: clean up console.logs
 //Adding to cart
function cartActionClick(event) {
    console.log("cartClicked");
    const saveActionElement = event.target;
    const saveActionUrl = ("/api/transaction/transactionEntry");
    saveActionElement.disabled = true;
    
    console.log("made it past initial variables");

    const saveTransactionEntryRequest = {
        id: "",
        transactionId: "00000000-0000-0000-0000-000000000000", 
        productId: getProductId(),
        quantity: getProductCount(),
        price: getProductPrice(),
    };

    console.log(saveTransactionEntryRequest);

    ajaxPost(saveActionUrl, saveTransactionEntryRequest), (callbackResponse) => {
        console.log("inside callback response");
        saveActionElement.disabled = false;
        console.log(callbackResponse);
        if(isSuccessResponse(callbackResponse))
        {
            console.log("saved!, but maybe not really");
			completeSaveAction(callbackResponse);
			displayProductAddToCartAlertModal();
        }
    }

	function displayProductAddToCartAlertModal() {
		if (hideProductAddToCartAlertTimer) {
			clearTimeout(hideProductAddToCartAlertTimer);
		}
	
		const addToCartAlertModalElement = getAddToCartAlertModalElement();
		addToCartAlertModalElement.style.display = "none";
		addToCartAlertModalElement.style.display = "block";
	
		hideProductAddToCartAlertTimer = setTimeout(hideProductAddToCartAlertModal, 1200);
	}
	
	function hideProductAddToCartAlertModal() {
		if (hideProductAddToCartAlertTimer) {
			clearTimeout(hideProductAddToCartAlertTimer);
		}
	
		getAddToCartAlertModalElement().style.display = "none";
	}

    
}
// End adding to cart

// Getters and setters
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
	return document.getElementById("productSavedAlertModal");
}

function getDeleteActionElement() {
	return document.getElementById("deleteButton");
}

function getCartActionElement() {
	return document.getElementById("cartButton");
}

function getAddToCartAlertModalElement() {
	return document.getElementById("productAddToCartAlertModal");
}

function getProductId() {
	return getProductIdElement().value;
}
function setProductId(productId) {
	getProductIdElement().value = productId;
}
function getProductIdElement() {
	return document.getElementById("productId");
}

function getProductLookupCode() {
	return getProductLookupCodeElement().value;
}
function getProductLookupCodeElement() {
	return document.getElementById("productLookupCode");
}

function getProductCount() {
	return Number(getProductCountElement().value);
}
function getProductCountElement() {
	return document.getElementById("productCount");
}

function getProductPrice() {
	return Number(getProductPriceElement().value);
}
function getProductPriceElement() {
	return document.getElementById("productPrice");
}
// End getters and setters
