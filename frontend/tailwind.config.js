/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      fontFamily: {
        ubuntu: ["Ubuntu", "sans-serif"],
      },
      dropShadow: {
        "md-top": "0 -4px 3px rgb(0 0 0 / 0.07)",
        "lg-inner": "inset 0 4px 5px 0 rgb(0 0 0 / 0.05)",
      },
    },
  },
  plugins: [require("daisyui"), require("@tailwindcss/forms")],
};
