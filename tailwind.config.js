/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/static/**/*.{html,js}",
  ],
  theme: {
    extend: {
      colors: {
        /* Primary: Blue */
        primary: {
          50:  '#eff6ff',
          100: '#dbeafe',
          200: '#bfdbfe',
          300: '#93c5fd',
          400: '#60a5fa',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
          800: '#1e3a5f',
          900: '#1e2d4a',
          DEFAULT: '#2563eb',    /* primary-600 */
          foreground: '#ffffff',
        },
        /* Accent: Pink */
        accent: {
          50:  '#fdf2f8',
          100: '#fce7f3',
          200: '#fbcfe8',
          300: '#f9a8d4',
          400: '#f472b6',
          500: '#ec4899',
          600: '#db2777',
          700: '#be185d',
          800: '#9d174d',
          900: '#831843',
          DEFAULT: '#ec4899',    /* accent-500 */
          foreground: '#ffffff',
        },
        /* Surface / Background */
        background: {
          DEFAULT: '#f8fafc',
          card: '#ffffff',
        },
        /* Border & Ring */
        border: {
          DEFAULT: '#e2e8f0',
        },
        ring: {
          DEFAULT: '#2563eb',    /* primary-600 */
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'sans-serif'],
      },
      borderRadius: {
        DEFAULT: '0.5rem',      /* rounded-lg as default */
      },
    },
  },
  plugins: [],
};
