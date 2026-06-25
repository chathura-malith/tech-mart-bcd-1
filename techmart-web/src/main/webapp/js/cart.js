
function changeQty(productId, change) {
    let qtyInput = document.getElementById('qty-' + productId);
    let currentQty = parseInt(qtyInput.value);
    let newQty = currentQty + change;

    if (newQty <= 0) {
        removeCartItem(productId);
        return;
    }

    fetch(contextPath + '/update-cart', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'productId=' + productId + '&quantity=' + newQty
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'unauthorized') {
                window.location.href = data.redirect;
                return;
            }
            if (data.status === 'success') {
                qtyInput.value = newQty;
                let price = parseFloat(document.getElementById('price-' + productId).innerText);
                document.getElementById('subtotal-' + productId).innerText = (price * newQty).toFixed(2);
                updateSummaryTotals(data.cartSize, data.totalAmount);
            }
        })
        .catch(error => console.error('Error updating quantity:', error));
}

function removeCartItem(productId) {
    if (confirm("Are you sure you want to remove this item?")) {
        fetch(contextPath + '/remove-from-cart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'productId=' + productId
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'unauthorized') {
                    window.location.href = data.redirect;
                    return;
                }
                if (data.status === 'success') {
                    let row = document.getElementById('product-row-' + productId);
                    row.remove();

                    if (data.cartSize === 0) {
                        location.reload();
                    } else {
                        updateSummaryTotals(data.cartSize, data.totalAmount);
                    }
                }
            })
            .catch(error => console.error('Error removing item:', error));
    }
}

function updateSummaryTotals(cartSize, totalAmount) {
    let badge = document.getElementById('cart-badge');
    if (badge) badge.innerText = cartSize;

    document.getElementById('summary-items').innerText = cartSize;
    document.getElementById('summary-total').innerText = parseFloat(totalAmount).toFixed(2);
}