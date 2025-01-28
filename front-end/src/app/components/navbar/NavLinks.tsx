import NavLink from './NavLink';

const NavLinks = () => {
  return (
    <ul className="hidden lg:flex items-center justify-start gap-6 md:gap-8 py-3 sm:justify-center">
      <NavLink href="#">Home</NavLink>
      <NavLink href="#">Best Sellers</NavLink>
      <NavLink href="#">Gift Ideas</NavLink>
      <NavLink href="#">Today aped&apos;s Deals</NavLink>
      <NavLink href="#">Sell</NavLink>
    </ul>
  );
};

export default NavLinks;
