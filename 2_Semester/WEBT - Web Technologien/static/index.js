import { generateGradientCSS, validateHex } from "./utils/utils.js";

const FAVORITES_ADD_URL = "/favorites/add";
const FAVORITES_URL = "/favorites";
const GRADIENTS_URL = "/gradients";

document.addEventListener("DOMContentLoaded", () => {
  renderFavorites();
  renderExplore();
  renderLastFavoriteFromCookie();

  document.getElementById("save").addEventListener("click", saveGradient);
  document.getElementById("copy").addEventListener("click", generateGradient);
  document
    .getElementById("direction")
    .addEventListener("change", generateGradient);

  document
    .querySelectorAll(".color-input")
    .forEach((input) => input.addEventListener("input", () => syncHex(input)));

  document.querySelectorAll(".hex-input").forEach((input) => {
    input.dataset.touched = "false";
    input.addEventListener("input", () => syncColor(input));
  });

  updateActionButtons();
});

function syncHex(colorInput) {
  const hexInput = colorInput.nextElementSibling;
  const errorMsg = hexInput.nextElementSibling;
  const val = colorInput.value.toUpperCase();

  hexInput.value = val;
  if (validateHex(val)) {
    clearError(hexInput, errorMsg);
    generateGradient();
  }

  updateActionButtons();
}

function syncColor(hexInput) {
  const colorInput = hexInput.previousElementSibling;
  const val = hexInput.value.trim();
  const errorMsg = hexInput.nextElementSibling;

  hexInput.dataset.touched = "true";

  if (!validateHex(val)) {
    if (hexInput.dataset.touched === "true") showError(hexInput, errorMsg);
  } else {
    colorInput.value = val;
    clearError(hexInput, errorMsg);
    generateGradient();
  }

  updateActionButtons();
}

const showError = (input, msg) => {
  input.classList.add("input-error");
  msg.textContent = "Invalid hex code (e.g. #123ABC)";
  msg.style.display = "block";
};

const clearError = (input, msg) => {
  input.classList.remove("input-error");
  msg.textContent = "";
  msg.style.display = "none";
};

function updateActionButtons() {
  const hasErrors = [...document.querySelectorAll(".hex-input")].some(
    (i) => !validateHex(i.value.trim()),
  );
  document.getElementById("save").disabled = hasErrors;
  document.getElementById("copy").disabled = hasErrors;
}

function generateGradient() {
  const l = document.getElementById("leftGradient").value;
  const r = document.getElementById("rightGradient").value;
  const d = document.getElementById("direction").value;
  document.querySelector(".gradient_preview").style.background =
    generateGradientCSS(d, l, r);
  drawColorStrip(l, r);
}

async function saveGradient() {
  const body = {
    left: document.getElementById("leftGradient").value,
    right: document.getElementById("rightGradient").value,
    direction: document.getElementById("direction").value,
  };

  const res = await fetch(FAVORITES_ADD_URL, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  res.ok ? renderFavorites() : alert("Failed to save gradient. Try again.");
}

async function renderFavorites() {
  const c = document.getElementById("favoritesList");
  c.innerHTML = "";
  try {
    const res = await fetch(FAVORITES_URL);
    (await res.json()).favorites?.forEach((g) => {
      const div = makeGradientDiv(g);
      c.appendChild(div);
    });
  } catch {
    c.textContent = "Failed to load favorites.";
  }
}

async function renderExplore() {
  const c = document.getElementById("exploreList");
  c.innerHTML = "";
  try {
    const res = await fetch(GRADIENTS_URL);
    (await res.json()).gradients?.forEach((g) => {
      const div = makeGradientDiv(g);
      c.appendChild(div);
    });
  } catch {
    c.textContent = "Failed to load gradients.";
  }
}

function makeGradientDiv(g) {
  const div = document.createElement("div");
  div.style.background = generateGradientCSS(g.direction, g.left, g.right);
  div.addEventListener("click", () => {
    document.getElementById("leftGradient").value = g.left;
    document.getElementById("rightGradient").value = g.right;
    document.getElementById("direction").value = g.direction;
    generateGradient();
  });
  return div;
}

function drawColorStrip(left, right) {
  const canvas = document.getElementById("gradientCanvas");
  const ctx = canvas.getContext("2d");
  const [w, h] = [canvas.width, canvas.height];
  const stripW = w / 10;
  const rgb = (hex) => {
    const n = parseInt(hex.replace("#", ""), 16);
    return { r: (n >> 16) & 255, g: (n >> 8) & 255, b: n & 255 };
  };

  const a = rgb(left),
    b = rgb(right);
  ctx.clearRect(0, 0, w, h);
  for (let i = 0; i < 10; i++) {
    const t = i / 9;
    const r = Math.round(a.r + t * (b.r - a.r));
    const g = Math.round(a.g + t * (b.g - a.g));
    const b_ = Math.round(a.b + t * (b.b - a.b));
    ctx.fillStyle = `rgb(${r}, ${g}, ${b_})`;
    ctx.fillRect(i * stripW, 0, stripW, h);
  }
}

function renderLastFavoriteFromCookie() {
  const cookie = Object.fromEntries(
    document.cookie.split("; ").map((c) => c.split("=")),
  ).lastFavorite;

  if (!cookie) return;
  try {
    const { left, right, direction } = JSON.parse(decodeURIComponent(cookie));
    document.getElementById("leftGradient").value = left;
    document.getElementById("rightGradient").value = right;
    document.getElementById("direction").value = direction;
    generateGradient();
  } catch (err) {
    console.warn("Invalid cookie:", err);
  }
}
