import { Product } from '../types/Product';
import ProductCard from './ProductCard';

const products:Product[] = [
  {
    id: 1,
    name: 'Apple iPhone 15',
    price: '$799',
    image: '/images/iphone-15.jpg',
    category: 'Electronics',  
},
  {
    id: 2,
    name: 'Sony Playstation 5',
    price: '$499',
    image: '/images/ps5.jpg',
    category: 'Gaming',
  },
  {
    id: 3,
    name: 'Apple Watch SE',
    price: '$299',
    image: '/images/apple-watch-se.jpg',
    category: 'Electronics',
  },
  {
    id: 4,
    name: 'Samsung 4K Smart TV',
    price: '$899',
    image: '/images/samsung-tv.jpg',
    category: 'TV/Projectors',
  },
  {
    id: 5,
    name: 'Nike Air Max',
    price: '$150',
    image: '/images/nike-air-max.jpg',
    category: 'Sport',
  },
  {
    id: 6,
    name: 'Canon EOS R5',
    price: '$3,899',
    image: '/images/canon-eos-r5.jpg',
    category: 'Electronics',
  },
];

const FeaturedProducts = () => {
  return (
    <section className="container mx-auto px-4 py-12">
      <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-8">
        Featured Products
      </h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </section>
  );
};

export default FeaturedProducts;
