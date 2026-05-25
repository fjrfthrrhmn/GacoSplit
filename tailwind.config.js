/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.{html,js}"
  ],
  theme: {
    extend: {
      colors: {
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
      },
      fontFamily: {
        sans: ["Geist", "ui-sans-serif", "sans-serif", "system-ui"],
        serif: ["Lora", "ui-serif", "serif"],
        mono: ["DM Mono", "monospace"],
      },
      borderRadius: {
        DEFAULT: "var(--radius)",
      },
      letterSpacing: {
        normal: "var(--tracking-normal)",
      },
    },
  },
  daisyui: {
    themes: [
      {
        gaco: {
          "primary": "#f04eb8",
          "primary-content": "#ffffff",
          "secondary": "#00b4d8",
          "secondary-content": "#ffffff",
          "accent": "#fef0f5",
          "accent-content": "#000000",
          "neutral": "#1d1d1f",
          "neutral-content": "#f5f5f7",
          "base-100": "#fdfcfc",
          "base-200": "#f5f5f5",
          "base-300": "#ebebeb",
          "base-content": "#000000",
          "info": "#3b82f6",
          "info-content": "#ffffff",
          "success": "#22c55e",
          "success-content": "#ffffff",
          "warning": "#f59e0b",
          "warning-content": "#ffffff",
          "error": "#e84a5f",
          "error-content": "#ffffff",
        },
      },
    ],
  },
  plugins: [require("daisyui")],
};
