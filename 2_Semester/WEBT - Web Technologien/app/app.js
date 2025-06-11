const express = require("express");
const fs = require("fs");
const path = require("path");

const app = express();
const PORT = 8001;

const favoritesPath = path.join(__dirname, "../data/favorites.json");
const gradientsPath = path.join(__dirname, "../data/gradients.json");

function validateFavoritesBody(req) {
  const re = new RegExp("^#(?:[0-9a-fA-F]{3}){1,2}$");
  return re.test(req.right) && re.test(req.left) && !!req.direction;
}

app.use(express.static(path.join(__dirname, "../static")));
app.use(express.json());

app.get("/gradients", (req, res) => {
  fs.readFile(gradientsPath, "utf8", (err, data) => {
    if (err)
      return res.status(500).json({ error: "Failed to read internal Data" });
    res.status(200).json({ ...JSON.parse(data) });
  });
});

app.get("/favorites", (req, res) => {
  fs.readFile(favoritesPath, "utf8", (err, data) => {
    if (err)
      return res.status(500).json({ error: "Failed to read internal Data" });
    res.status(200).json({ ...JSON.parse(data) });
  });
});

app.put("/favorites/add", (req, res) => {
  const newFavorite = req.body;

  if (!validateFavoritesBody(newFavorite)) {
    return res.status(400).json({ error: "Invalid request Body" });
  }

  fs.readFile(favoritesPath, "utf-8", (err, data) => {
    if (err)
      return res.status(500).json({ error: "Failed to read internal data" });

    const json = JSON.parse(data);
    const exists = json.favorites.some(
      (fav) =>
        fav.left === newFavorite.left &&
        fav.right === newFavorite.right &&
        fav.direction === newFavorite.direction,
    );

    if (exists) return;

    json.favorites.push(newFavorite);

    fs.writeFile(favoritesPath, JSON.stringify(json, null, 2), (err) => {
      if (err)
        return res.status(500).json({ error: "Failed to update favorites." });

      res.cookie("lastFavorite", JSON.stringify(newFavorite), {
        maxAge: 86400000,
        httpOnly: false,
      });

      res.status(200).json({ message: "Favorite added successfully." });
    });
  });
});

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});