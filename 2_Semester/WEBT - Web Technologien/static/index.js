import { generateGradientCSS, validateHex } from "./utils/utils.js";

const FAVORITES_ADD_URL = "/favorites/add";
const FAVORITES_URL = "/favorites";
const GRADIENTS_URL = "/gradients";

document.addEventListener("DOMContentLoaded", () => {
  generateGradient();
  renderFavorites();
  renderExplore();

  document.getElementById("save").addEventListener("click", saveGradient);

  document.querySelectorAll(".color-input").forEach((input) => {
    input.addEventListener("input", (e) => syncHex(e.target));
  });

  document.querySelectorAll(".hex-input").forEach((input) => {
    input.addEventListener("input", (e) => syncColor(e.target));
  });

  document
    .querySelector("button")
    .addEventListener("click", () => generateGradient());
});

function syncHex(colorInput) {
  const hexInput = colorInput.nextElementSibling;
  hexInput.value = colorInput.value.toUpperCase();
  generateGradient();
}

function syncColor(hexInput) {
  const colorInput = hexInput.previousElementSibling;
  const val = hexInput.value.trim();

  if (validateHex(val)) {
    colorInput.value = val;
    generateGradient();
  }
}

function generateGradient() {
  const left = document.getElementById("leftGradient").value;
  const right = document.getElementById("rightGradient").value;

  const preview = document.querySelector(".gradient_preview");
  preview.style.background = generateGradientCSS("left", left, right);
}

async function saveGradient() {
  const right = document.getElementById("rightGradient").value;
  const left = document.getElementById("leftGradient").value;

  const res = await fetch(FAVORITES_ADD_URL, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      right,
      left,
      direction: "right",
    }),
  });

  if (!res.ok) {
    alert("Failed to safe gradient. Try again.");
  } else {
    renderFavorites();
  }
}

async function renderFavorites() {
  const container = document.getElementById("favoritesList");
  container.innerHTML = "";

  try {
    const res = await fetch(FAVORITES_URL);
    const data = await res.json();
    const gradients = data.favorites || [];

    gradients.forEach((g) => {
      const div = document.createElement("div");
      div.style.height = "100px";
      div.style.margin = "10px";
      div.style.border = "1px solid #ccc";
      div.style.borderRadius = "6px";
      div.style.background = generateGradientCSS(g.direction, g.left, g.right);
      container.appendChild(div);
    });
  } catch (err) {
    container.textContent = "Failed to load favorites.";
  }
}

async function renderExplore() {
  const container = document.getElementById("exploreList");
  container.innerHTML = "";

  try {
    const res = await fetch(GRADIENTS_URL);
    const data = await res.json();
    const gradients = data.gradients || [];

    console.log(gradients);

    gradients.forEach((g) => {
      const div = document.createElement("div");
      div.style.height = "100px";
      div.style.margin = "10px";
      div.style.border = "1px solid #ccc";
      div.style.borderRadius = "6px";
      div.style.background = generateGradientCSS(g.direction, g.left, g.right);
      container.appendChild(div);
    });
  } catch (err) {
    container.textContent = "Failed to load gradients.";
  }
}
