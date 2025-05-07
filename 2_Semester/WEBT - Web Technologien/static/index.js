import { generateGradientCSS, validateHex } from "./utils/utils.js";

const FAVORITES_ADD_URL = "/favorites/add";
const FAVORITES_URL = "/favorites";
const GRADIENTS_URL = "/gradients";

document.addEventListener("DOMContentLoaded", () => {
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

  document
    .getElementById("save")
    .addEventListener("click", () => saveGradient());
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
    console.error("Failed to save gradient", await res.json());
  } else {
    console.log("Gradient saved!");
  }
}
