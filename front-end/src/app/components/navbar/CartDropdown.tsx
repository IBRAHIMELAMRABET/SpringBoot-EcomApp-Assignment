'use client';

import { useState } from 'react';
import Link from 'next/link';
import { CartItem } from '@/app/types/CartItem';


const CartDropdown = () => {
  const [isOpen, setIsOpen] = useState(false);

  const cartItems: CartItem[] = [
    { name: 'Apple iPhone 15', price: '$599', quantity: 1 },
    { name: 'Apple iPad Air', price: '$499', quantity: 1 },
    { name: 'Apple Watch SE', price: '$598', quantity: 2 },
    { name: 'Sony Playstation 5', price: '$799', quantity: 1 },
    { name: 'Apple iMac 20"', price: '$8,997', quantity: 3 },
  ];

  return (
    <div className="relative">
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="inline-flex items-center rounded-lg justify-center p-2 hover:bg-gray-100 dark:hover:bg-gray-700 text-sm font-medium leading-none text-gray-900 dark:text-white"
      >
        <span className="sr-only">Cart</span>
        <svg
          className="w-5 h-5 lg:me-1"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          fill="none"
          viewBox="0 0 24 24"
        >
          <path
            stroke="currentColor"
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M5 4h1.5L9 16m0 0h8m-8 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4Zm8 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4Zm-8.5-3h9.25L19 7H7.312"
          />
        </svg>
        <span className="hidden sm:flex">My Cart</span>
        <svg
          className="hidden sm:flex w-4 h-4 text-gray-900 dark:text-white ms-1"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          fill="none"
          viewBox="0 0 24 24"
        >
          <path
            stroke="currentColor"
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="m19 9-7 7-7-7"
          />
        </svg>
      </button>

      {isOpen && (
        <div className="absolute z-10 w-96 space-y-4 overflow-hidden rounded-lg bg-white p-4 antialiased shadow-lg dark:bg-gray-800">
          {cartItems.map((item, index) => (
            <div key={index} className="grid grid-cols-2">
              <div>
                <Link
                  href="#"
                  className="truncate text-sm font-semibold leading-none text-gray-900 dark:text-white hover:underline"
                >
                  {item.name}
                </Link>
                <p className="mt-0.5 truncate text-sm font-normal text-gray-500 dark:text-gray-400">
                  {item.price}
                </p>
              </div>
              <div className="flex items-center justify-end gap-6">
                <p className="text-sm font-normal leading-none text-gray-500 dark:text-gray-400">
                  Qty: {item.quantity}
                </p>
                <button
                  type="button"
                  className="text-red-600 hover:text-red-700 dark:text-red-500 dark:hover:text-red-600"
                >
                  <span className="sr-only">Remove</span>
                  <svg
                    className="h-4 w-4"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      fillRule="evenodd"
                      d="M2 12a10 10 0 1 1 20 0 10 10 0 0 1-20 0Zm7.7-3.7a1 1 0 0 0-1.4 1.4l2.3 2.3-2.3 2.3a1 1 0 1 0 1.4 1.4l2.3-2.3 2.3 2.3a1 1 0 0 0 1.4-1.4L13.4 12l2.3-2.3a1 1 0 0 0-1.4-1.4L12 10.6 9.7 8.3Z"
                      clipRule="evenodd"
                    />
                  </svg>
                </button>
              </div>
            </div>
          ))}
          <Link
            href="#"
            className="mb-2 me-2 inline-flex w-full items-center justify-center rounded-lg bg-primary-700 px-5 py-2.5 text-sm font-medium text-white hover:bg-primary-800 focus:outline-none focus:ring-4 focus:ring-primary-300 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
          >
            Proceed to Checkout
          </Link>
        </div>
      )}
    </div>
  );
};

export default CartDropdown;
