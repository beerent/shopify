export function formatPrice(price) {
  return numberWithCommas(price.toFixed(2))
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}