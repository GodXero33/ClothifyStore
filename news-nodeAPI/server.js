const express = require("express");

const app = express();
const PORT = 3000;

const news = [
    { headline: "Fashion Week 2025 Unveils Sustainable Clothing Trends." },
    { headline: "Major Clothing Brand Shifts to 100% Recycled Fabrics." },
    { headline: "AI-Powered Tailoring: The Future of Custom Fit Clothing?" },
    { headline: "Fast Fashion Faces Backlash Over Environmental Impact." },
    { headline: "Luxury Fashion Houses Embrace Virtual Runways." },
    { headline: "New Smart Fabrics Can Regulate Body Temperature." },
    { headline: "Retail Giants Compete to Dominate Online Fashion Sales." },
    { headline: "Clothing Industry Sees Surge in Demand for Ethical Brands." },
    { headline: "3D Printing Revolutionizes High-End Fashion Manufacturing." },
    { headline: "Second-Hand Fashion Market Booms Among Gen Z Consumers." },
    { headline: "Major Brand Faces Lawsuit Over Unethical Labor Practices." },
    { headline: "Self-Cleaning Clothes: The Next Big Innovation?" },
    { headline: "Blockchain Technology Introduced for Luxury Brand Authentication." },
    { headline: "Sneaker Market Hits $100 Billion in Global Sales." },
    { headline: "Athleisure Continues to Dominate Casual Fashion Trends." },
    { headline: "E-commerce Platforms Battle for Fastest Clothing Delivery." },
    { headline: "Fashion Retailers Experiment with AI-Driven Styling Assistants." },
    { headline: "Sustainable Dyes Gain Popularity in Textile Industry." },
    { headline: "Clothing Subscription Services Gain Popularity Among Millennials." },
    { headline: "Eco-Friendly Packaging Becomes a Standard in Fashion Retail." }
];

app.get("/latest-news", (req, res) => {
    const randomNews = news[Math.floor(Math.random() * news.length)];
    res.json(randomNews);
});

app.listen(PORT, () => {
    console.log(`🚀 News API running at http://localhost:${PORT}`);
});
