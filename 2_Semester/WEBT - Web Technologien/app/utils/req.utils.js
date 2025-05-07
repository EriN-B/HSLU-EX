export function validateFavoritesBody(req) {
  const re = new RegExp("^#(?:[0-9a-fA-F]{3}){1,2}$");
  return re.test(req.right) && re.test(req.left) && !!req.direction;
}
