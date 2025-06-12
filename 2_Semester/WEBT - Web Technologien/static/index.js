import { generateGradientCSS, validateHex } from "./utils/utils.js";

const FAVORITES_ADD_URL = "/favorites/add",
  FAVORITES_URL = "/favorites",
  GRADIENTS_URL = "/gradients";

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

function syncHex(input) {
  const hexInput = input.nextElementSibling,
    errorMsg = hexInput.nextElementSibling,
    val = input.value.toUpperCase();
  hexInput.value = val;
  if (validateHex(val)) {
    clearError(hexInput, errorMsg);
    generateGradient();
  }
  updateActionButtons();
}

function syncColor(input) {
  const colorInput = input.previousElementSibling,
    val = input.value.trim(),
    errorMsg = input.nextElementSibling;
  input.dataset.touched = "true";
  if (validateHex(val)) {
    colorInput.value = val;
    clearError(input, errorMsg);
    generateGradient();
  } else if (input.dataset.touched === "true") showError(input, errorMsg);
  updateActionButtons();
}

const showError = (input, msg) => {
  input.classList.add("input-error");
  msg.textContent = "Invalid hex code";
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
  const l = document.getElementById("leftGradient").value,
    r = document.getElementById("rightGradient").value,
    d = document.getElementById("direction").value;
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
    (await res.json()).favorites?.forEach((g) =>
      c.appendChild(makeGradientDiv(g)),
    );
  } catch {
    c.textContent = "Failed to load favorites.";
  }
}

async function renderExplore() {
  const c = document.getElementById("exploreList");
  c.innerHTML = "";
  try {
    const res = await fetch(GRADIENTS_URL);
    (await res.json()).gradients?.forEach((g) =>
      c.appendChild(makeGradientDiv(g)),
    );
  } catch {
    c.textContent = "Failed to load gradients.";
  }
}

function makeGradientDiv(g) {
  const div = document.createElement("div");
  div.style.background = generateGradientCSS(g.direction, g.left, g.right);
  div.addEventListener("click", () => {
    const leftInput = document.getElementById("leftGradient");
    const rightInput = document.getElementById("rightGradient");
    leftInput.value = g.left;
    rightInput.value = g.right;
    document.getElementById("direction").value = g.direction;
    // Update hex inputs
    syncHex(leftInput);
    syncHex(rightInput);
    generateGradient();
    document
      .getElementById("gradientCanvas")
      .scrollIntoView({ behavior: "smooth", block: "center" });
  });
  return div;
}

function drawColorStrip(left, right) {
  const canvas = document.getElementById("gradientCanvas"),
    ctx = canvas.getContext("2d"),
    w = canvas.width,
    h = canvas.height;
  const rgb = (hex) => ({
    r: (parseInt(hex.replace("#", ""), 16) >> 16) & 255,
    g: (parseInt(hex.replace("#", ""), 16) >> 8) & 255,
    b: parseInt(hex.replace("#", ""), 16) & 255,
  });
  const [a, b] = [rgb(left), rgb(right)];
  ctx.clearRect(0, 0, w, h);
  for (let i = 0; i < 10; i++) {
    const t = i / 9,
      r = Math.round(a.r + t * (b.r - a.r)),
      g = Math.round(a.g + t * (b.g - a.g)),
      b_ = Math.round(a.b + t * (b.b - a.b));
    ctx.fillStyle = `rgb(${r}, ${g}, ${b_})`;
    ctx.fillRect(i * (w / 10), 0, w / 10, h);
  }
}

function renderLastFavoriteFromCookie() {
  const cookie = Object.fromEntries(
    document.cookie.split("; ").map((c) => c.split("=")),
  ).lastFavorite;
  if (!cookie) return;
  try {
    const { left, right, direction } = JSON.parse(decodeURIComponent(cookie));
    const leftInput = document.getElementById("leftGradient");
    const rightInput = document.getElementById("rightGradient");
    leftInput.value = left;
    rightInput.value = right;
    document.getElementById("direction").value = direction;
    // Update hex inputs
    syncHex(leftInput);
    syncHex(rightInput);
    generateGradient();
  } catch (err) {
    console.warn("Invalid cookie:", err);
  }
}
