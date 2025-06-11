export function validateHex(value) {
  return /^#[0-9A-Fa-f]{6}$/.test(value);
}

export function generateGradientCSS(direction, left, right) {
  return `linear-gradient(${direction}, ${left}, ${right})`;
}
