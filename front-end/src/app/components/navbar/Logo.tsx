import Link from 'next/link';
import Image from 'next/image';

const Logo = () => {
  return (
    <div className="shrink-0">
      <Link href="#">
        {/* Light Mode Logo */}
        <div className="block w-24 h-10 dark:hidden"> {/* Adjust width and height here */}
          <Image
            src="/Shop_logo.png" // Absolute path from the public folder
            alt="Logo"
            width={96} // Adjust width
            height={32} // Adjust height
            layout="responsive" // Use "responsive" for flexible sizing
          />
        </div>
        {/* Dark Mode Logo */}
        <div className="hidden w-24 h-10 dark:block"> {/* Adjust width and height here */}
          <Image
            src="/Shop_logo.png" // Absolute path from the public folder
            alt="Dark Logo"
            width={96} // Adjust width
            height={32} // Adjust height
            layout="responsive" // Use "responsive" for flexible sizing
          />
        </div>
      </Link>
    </div>
  );
};

export default Logo;
