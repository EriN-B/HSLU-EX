import { generateGradientCSS, validateHex } from "./utils/utils.js";

const FAVORITES_ADD_URL = "/favorites/add",
  FAVORITES_DELETE_URL = "/favorites/delete",
  FAVORITES_URL = "/favorites",
  GRADIENTS_URL = "/gradients";

document.addEventListener("DOMContentLoaded", () => {
  renderFavorites();
  renderExplore();
  renderLastFavoriteFromCookie();

  document.getElementById("save").addEventListener("click", saveGradient);
  document.getElementById("copy").addEventListener("click", copyGradient);
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
  generateGradient();
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

  if (res.ok) {
    await renderFavorites();
  } else {
    const message=await res.json()
    alert(message.error ?? "Failed to save gradient. Try again.");
  }
}

async function renderFavorites() {
  const c = document.getElementById("favoritesList");
  c.innerHTML = "";
  try {
    const res = await fetch(FAVORITES_URL);
    (await res.json()).favorites?.forEach((g) =>
      c.appendChild(makeGradientDiv(g, true)),
    );
  } catch {
    c.textContent = "Failed to load favorites.";
  }
}

async function deleteFavorite(gradient) {
  try {
    const deletePayload = {
      uuid: gradient.uuid,
      left: gradient.left,
      right: gradient.right,
      direction: gradient.direction
    };

    const res = await fetch(FAVORITES_DELETE_URL, {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(deletePayload),
    });

    if (res.ok) {
      await renderFavorites();
    } else {
      const errorData = await res.json();
      alert(`Failed to delete favorite: ${errorData.error || 'Unknown error'}`);
    }
  } catch (error) {
    alert("Failed to delete favorite. Please try again.");
    console.error("Error deleting favorite:", error);
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

function makeGradientDiv(g, isFavorite = false) {
  const div = document.createElement("div");
  div.style.background = generateGradientCSS(g.direction, g.left, g.right);
  div.className = "gradient-item";

  div.addEventListener("click", (e) => {
    if (e.target.classList.contains('delete-btn')) return;

    const leftInput = document.getElementById("leftGradient");
    const rightInput = document.getElementById("rightGradient");
    leftInput.value = g.left;
    rightInput.value = g.right;
    document.getElementById("direction").value = g.direction;
    syncHex(leftInput);
    syncHex(rightInput);
    generateGradient();
    document
      .getElementById("gradientCanvas")
      .scrollIntoView({ behavior: "smooth", block: "center" });
  });

  if (isFavorite) {
    const deleteBtn = document.createElement("button");
    deleteBtn.className = "delete-btn";
    deleteBtn.textContent = "×";
    deleteBtn.title = "Delete favorite";
    deleteBtn.addEventListener("click", (e) => {
      e.stopPropagation();
      deleteFavorite(g);
    });
    div.appendChild(deleteBtn);
  }

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

function copyGradient() {
  const l = document.getElementById("leftGradient").value,
    r = document.getElementById("rightGradient").value,
    d = document.getElementById("direction").value;

  const gradientCSS = generateGradientCSS(d, l, r);

  navigator.clipboard.writeText(gradientCSS)
    .then(() => {
      const copyBtn = document.getElementById("copy");
      const originalText = copyBtn.textContent;
      copyBtn.textContent = "Copied!";

      setTimeout(() => {
        copyBtn.textContent = originalText;
      }, 2000);
    })
    .catch(err => {
      console.error("Failed to copy gradient: ", err);
      alert("Failed to copy gradient to clipboard");
    });
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
    syncHex(leftInput);
    syncHex(rightInput);
    generateGradient();
  } catch (err) {
    console.warn("Invalid cookie:", err);
  }
}
