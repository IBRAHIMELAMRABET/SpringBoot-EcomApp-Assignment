import Image from 'next/image';
import Link from 'next/link';
import { Product } from '../types/Product';


const ProductCard = ({ product }: { product: Product }) => {
  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
      <Link href={`/products/${product.id}`}>
        <div className="relative h-48">
          <Image
            src={product.image}
            alt={product.name}
            fill
            className="object-cover"
          />
        </div>
        <div className="p-4">
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white">
            {product.name}
          </h3>
          <p className="text-gray-700 dark:text-gray-300">{product.price}</p>
        </div>
      </Link>
    </div>
  );
};

export default ProductCard;
