'use client';

import { useEffect, useState } from 'react';
import Image from 'next/image'; 
import {
  FiServer, 
  FiShoppingBag,
  FiSmartphone,
  FiTv, 
  FiPackage,
  FiDribbble,
  FiShoppingCart,
} from 'react-icons/fi'; 

import {FaGamepad} from 'react-icons/fa'; 

const HeroSection = () => {
  const [activeIndex, setActiveIndex] = useState(0);

  const images = [
    '/images/carousel-1.jpg',
    '/images/carousel-2.jpg',
    '/images/carousel-3.jpg',
    '/images/carousel-4.jpg',
    '/images/carousel-5.jpg',
  ];

  const categories = [
    { name: 'Servers', icon: <FiServer className="w-8 h-8" /> },
    { name: 'Fashion', icon: <FiShoppingBag className="w-8 h-8" /> },
    { name: 'Electronics', icon: <FiSmartphone className="w-8 h-8" /> },
    { name: 'Gaming', icon: <FaGamepad className="w-8 h-8" /> },
    { name: 'TV/Projectors', icon: <FiTv className="w-8 h-8" /> },
    { name: 'Toys', icon: <FiPackage className="w-8 h-8" /> },
    { name: 'Sport', icon: <FiDribbble className="w-8 h-8" /> },
    { name: 'Grocery', icon: <FiShoppingCart className="w-8 h-8" /> },
  ];

  useEffect(() => {
    const interval = setInterval(() => {
      setActiveIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 5000); // Change image every 5 seconds

    return () => clearInterval(interval);
  }, [images.length]);

  return (
    <div className="relative w-full h-[400px] md:h-[600px] overflow-hidden">
      {/* Carousel Background */}
      <div className="absolute inset-0">
        {images.map((src, index) => (
          <div
            key={index}
            className={`absolute inset-0 transition-opacity duration-1000 ease-in-out ${
              index === activeIndex ? 'opacity-100' : 'opacity-0'
            }`}
          >
            <Image
              src={src}
              alt={`Carousel Image ${index + 1}`}
              fill // Fill the parent container
              className="object-cover"
              priority={index === activeIndex} // Prioritize loading the active image
            />
          </div>
        ))}
      </div>

      {/* Hero Content */}
      <div className="absolute inset-0 flex flex-col items-center justify-center bg-black bg-opacity-50 text-white text-center p-4">
        <h1 className="text-4xl md:text-6xl font-bold mb-4">
          Don&apos;t Miss Out
        </h1>
        <p className="text-lg md:text-2xl mb-8">
          Limited Stock at Rock-Bottom Prices!
        </p>
        <button className="text-white px-8 py-3 rounded-lg text-lg font-semibold transition-colors bg-gradient-to-tl from-pink-500 via-purple-500 to-green-500 hover:bg-gradient-to-tl hover:from-pink-600 hover:via-purple-600 hover:to-green-600">
            Shop Now
        </button>
      </div>

      {/* Categories Section */}
      <div className="absolute bottom-0 left-0 right-0 bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm py-4">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-8 gap-4">
            {categories.map((category, index) => (
              <div
                key={index}
                className="flex flex-col items-center justify-center p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors cursor-pointer"
              >
                <span className="text-gray-900 dark:text-white">
                  {category.icon}
                </span>
                <span className="text-sm font-medium text-gray-900 dark:text-white">
                  {category.name}
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default HeroSection;
