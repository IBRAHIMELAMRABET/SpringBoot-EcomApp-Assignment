import Link from 'next/link';

const MobileMenu = () => {
  return (
    <div id="ecommerce-navbar-menu-1" className="bg-gray-50 dark:bg-gray-700 dark:border-gray-600 border border-gray-200 rounded-lg py-3 hidden px-4 mt-4">
      <ul className="text-gray-900 dark:text-white text-sm font-medium dark:text-white space-y-3">
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Home
          </Link>
        </li>
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Best Sellers
          </Link>
        </li>
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Gift Ideas
          </Link>
        </li>
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Games
          </Link>
        </li>
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Electronics
          </Link>
        </li>
        <li>
          <Link href="#" className="hover:text-primary-700 dark:hover:text-primary-500">
            Home & Garden
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default MobileMenu;
