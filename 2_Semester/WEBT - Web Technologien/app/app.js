const express = require("express");
const fs = require("fs");
const path = require("path");
const { validateFavoritesBody } = require("./utils/req.utils");

const app = express();
const PORT = 8001;

const favoritesPath = path.join(__dirname, "../data/favorites.json");
const gradientsPath = path.join(__dirname, "../data/gradients.json");

app.use(express.static(path.join(__dirname, "../static")));
app.use(express.json());

app.get("/gradients", (req, res) => {
  fs.readFile(gradientsPath, "utf8", (err, data) => {
    if (err) res.status(500).json({ error: "Failed to read internal Data" });
    return res.status(200).json({ ...JSON.parse(data) });
  });
});

app.get("/favorites", (req, res) => {
  fs.readFile(favoritesPath, "utf8", (err, data) => {
    if (err) res.status(500).json({ error: "Failed to read internal Data" });
    return res.status(200).json({ ...JSON.parse(data) });
  });
});

app.put("/favorites/add", (req, res) => {
  const newFavorite = req.body;

  if (!validateFavoritesBody(req.body)) {
    return res.status(400).json({ error: "Invalid request Body" });
  }

  fs.readFile(favoritesPath, "utf-8", (err, data) => {
    if (err) {
      return res.status(500).json({ error: "Failed to read internal data" });
    }

    const json = JSON.parse(data);

    try {
      json.favorites.push(newFavorite);
    } catch (err) {
      return res.status(500).json({ error: "Failed to update favorites." });
    }

    fs.writeFile(favoritesPath, JSON.stringify(json, null, 2), (err) => {
      if (err) {
        return res.status(500).json({ error: "Failed to update favorites." });
      }
      res.status(200).json({ message: "Favorite added successfully." });
    });
  });
});

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
