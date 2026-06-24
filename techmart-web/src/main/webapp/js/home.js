function addToCart(productId) {
    fetch(contextPath + '/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'productId=' + productId + '&quantity=1'
    })
        .then(response => response.json())
        .then(data => {
            if(data.status === 'success') {
                let badge = document.getElementById('cart-badge');
                if(badge) {
                    badge.innerText = data.cartSize;

                    badge.classList.add('bg-success');
                    badge.classList.remove('bg-danger');
                    setTimeout(() => {
                        badge.classList.remove('bg-success');
                        badge.classList.add('bg-danger');
                    }, 500);
                }

                const toastElement = document.getElementById('cartToast');
                const toast = new bootstrap.Toast(toastElement, { delay: 3000 });
                toast.show();
            }
        })
        .catch(error => console.error('Error adding to cart:', error));
}