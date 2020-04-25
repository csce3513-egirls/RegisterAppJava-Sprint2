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

function productClick(event) {
    let listItem = findClickedListItemElement(event.target);

	window.location.assign(
		"/transactionDetail/"
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
