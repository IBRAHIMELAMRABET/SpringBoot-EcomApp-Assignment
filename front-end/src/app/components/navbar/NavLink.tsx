import Link from 'next/link';

interface NavLinkProps {
  href: string;
  children: React.ReactNode;
}

const NavLink: React.FC<NavLinkProps> = ({ href, children }) => {
  return (
    <li className="shrink-0">
      <Link
        href={href}
        className="flex text-sm font-medium text-gray-900 hover:text-primary-700 dark:text-white dark:hover:text-primary-500"
      >
        {children}
      </Link>
    </li>
  );
};

export default NavLink;
