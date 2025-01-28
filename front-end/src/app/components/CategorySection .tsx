import Image from 'next/image';
import Link from 'next/link';
import { Category } from '../types/Category';

const categories: Category[] = [
  {
    id: 1,
    name: 'Handbag',
    image: '/images/handbag.jpg',
    discount: '25% Off',
    link: '/categories/handbag',
  },
  {
    id: 2,
    name: 'Watch',
    image: '/images/watch.jpg',
    discount: '45% Off',
    link: '/categories/watch',
  },
  {
    id: 3,
    name: "Women's Style",
    image: '/images/womens-style.jpg',
    discount: 'New Arrivals',
    link: '/categories/womens-style',
  },
  {
    id: 4,
    name: 'Accessories',
    image: '/images/accessories.jpg',
    discount: 'Up to 70% Off',
    link: '/categories/accessories',
  },
  {
    id: 5,
    name: 'Backpack',
    image: '/images/backpack.jpg',
    discount: 'Min. 40-80% Off',
    link: '/categories/backpack',
  },
];

const CategorySection = () => {
  return (
    <section className="container mx-auto px-4 py-12">
      <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-8">
        Shop by Category
      </h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
        {categories.map((category) => (
          <div
            key={category.id}
            className="relative bg-white dark:bg-gray-800 rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow"
          >
            <Link href={category.link}>
              <div className="relative h-48">
                <Image
                  src={category.image}
                  alt={category.name}
                  fill
                  className="object-cover"
                />
                {category.discount && (
                  <div className="absolute top-2 left-2 bg-primary-700 text-white px-3 py-1 rounded-full text-sm font-semibold">
                    {category.discount}
                  </div>
                )}
              </div>
              <div className="p-4">
                <h3 className="text-lg font-semibold text-gray-900 dark:text-white">
                  {category.name}
                </h3>
                <button className="mt-2 bg-primary-700 text-white px-4 py-2 rounded-lg text-sm font-semibold hover:bg-primary-800 transition-colors">
                  Shop Now
                </button>
              </div>
            </Link>
          </div>
        ))}
      </div>
    </section>
  );
};

export default CategorySection;
