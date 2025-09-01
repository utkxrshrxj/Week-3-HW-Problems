package week3.assignment;

public class ShoppingApp {
    // Product Class
    static class Product {
        private String productId;
        private String productName;
        private double price;
        private String category;
        private int stockQuantity;

        private static int totalProducts = 0;

        public Product(String id, String name, double price, String category, int qty) {
            this.productId = id;
            this.productName = name;
            this.price = price;
            this.category = category;
            this.stockQuantity = qty;
            totalProducts++;
        }

        // Getters
        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public int getStockQuantity() { return stockQuantity; }

        // Update stock
        public void reduceStock(int qty) { this.stockQuantity -= qty; }
        public void increaseStock(int qty) { this.stockQuantity += qty; }

        public static int getTotalProducts() { return totalProducts; }
    }

    // ShoppingCart Class
    static class ShoppingCart {
        private String cartId;
        private String customerName;
        private Product[] products;
        private int[] quantities;
        private int itemCount;
        private double cartTotal;

        public ShoppingCart(String id, String name) {
            this.cartId = id;
            this.customerName = name;
            this.products = new Product[10]; // Max 10 products
            this.quantities = new int[10];
            this.itemCount = 0;
            this.cartTotal = 0;
        }

        public void addProduct(Product product, int qty) {
            if (product.getStockQuantity() >= qty) {
                products[itemCount] = product;
                quantities[itemCount] = qty;
                product.reduceStock(qty);
                itemCount++;
                calculateTotal();
                System.out.println(qty + " " + product.getProductName() + " added to cart.");
            } else {
                System.out.println("❌ Not enough stock for " + product.getProductName());
            }
        }

        public void removeProduct(String productId) {
            for (int i = 0; i < itemCount; i++) {
                if (products[i] != null && products[i].getProductId().equals(productId)) {
                    products[i].increaseStock(quantities[i]); // return stock
                    System.out.println(products[i].getProductName() + " removed from cart.");
                    products[i] = null;
                    quantities[i] = 0;
                    break;
                }
            }
            calculateTotal();
        }

        private void calculateTotal() {
            cartTotal = 0;
            for (int i = 0; i < itemCount; i++) {
                if (products[i] != null) {
                    cartTotal += products[i].getPrice() * quantities[i];
                }
            }
        }

        public void displayCart() {
            System.out.println("\n🛒 Cart for " + customerName);
            for (int i = 0; i < itemCount; i++) {
                if (products[i] != null) {
                    System.out.println(products[i].getProductName() + " x" + quantities[i] +
                            " = " + (products[i].getPrice() * quantities[i]));
                }
            }
            System.out.println("Cart Total: " + cartTotal);
        }

        public void checkout() {
            System.out.println(customerName + " checked out. Total Bill = " + cartTotal);
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        Product laptop = new Product("P1", "Laptop", 50000, "Electronics", 5);
        Product mobile = new Product("P2", "Mobile", 20000, "Electronics", 10);
        Product shoes = new Product("P3", "Shoes", 1500, "Fashion", 20);

        ShoppingCart cart = new ShoppingCart("C1", "Alice");

        cart.addProduct(laptop, 1);
        cart.addProduct(mobile, 2);
        cart.displayCart();

        cart.removeProduct("P2");
        cart.displayCart();

        cart.checkout();
    }
}
