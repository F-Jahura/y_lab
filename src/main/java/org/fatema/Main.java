package org.fatema;

import org.fatema.model.Product;
import org.fatema.utils.ProductUtils;
import org.fatema.repository.ProductRepository;
import org.fatema.service.AuthService;
import org.fatema.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int ADD_PRODUCT = 1;
    private static final int UPDATE_PRODUCT = 2;
    private static final int DELETE_PRODUCT = 3;
    private static final int ALL_PRODUCT = 4;
    private static final int FIND_PRODUCT_ID = 5;
    private static final int FIND_PRODUCT_CATEGORY = 6;
    private static final int FIND_PRODUCT_NAME = 7;
    private static final int FIND_PRODUCT_BRAND = 8;
    private static final int FIND_PRODUCT_PRICE = 9;
    private static final int EXIT = 0;


    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        ProductService service = new ProductService(productRepository);
        AuthService authService = new AuthService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter login:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            if (authService.login(username, password)) {
                System.out.println("Successfully sign in");
                break;
            } else {
                System.out.println("Login or password is not correct. Please try again!");
            }
        }

        while (true) {
            System.out.println("\nPlease choose the number and press Enter!");
            System.out.print("Menu: \n" + ADD_PRODUCT + " - Add product \n" + UPDATE_PRODUCT + " - Update product \n"
                    + DELETE_PRODUCT + " - Delete product \n" + ALL_PRODUCT + " - Product list \n"
                    + FIND_PRODUCT_ID + " - Find product by Id \n" + FIND_PRODUCT_CATEGORY + " - Find product by category \n"
                    + FIND_PRODUCT_NAME + " - Find product by name \n" + FIND_PRODUCT_BRAND + " - Find product by brand \n"
                    + FIND_PRODUCT_PRICE + " - Find product in price range \n" + EXIT + " - Exit \n");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (!authService.isAuthorized()) {
                System.out.println("You are not authorized");
                continue;
             }

            switch (option) {
                case ADD_PRODUCT:
                    Product product = service.addProduct(ProductUtils.entryProductDetails());
                    ProductUtils.printProduct(product);
                    break;
                case UPDATE_PRODUCT:
                    Product updateList = service.updateProduct(ProductUtils.entryProductDetails());
                    System.out.println(updateList);
                    break;
                case DELETE_PRODUCT:
                    System.out.println("Input Id: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    service.removeProduct(id);
                    System.out.println("The product is deleted.");
                    break;
                case ALL_PRODUCT:
                    ProductUtils.printProducts(service.getAllProducts());
                    break;
                case FIND_PRODUCT_ID:
                    System.out.println("Input ID: ");
                    Long findId = scanner.nextLong();
                    scanner.nextLine();
                    Product p = service.getProductById(findId);
                    if (p != null) {
                        System.out.println(p);
                    } else {
                        System.out.println("The product is not found.");
                    }
                    break;
                case FIND_PRODUCT_CATEGORY:
                    System.out.println("Input category: ");
                    String category = scanner.nextLine();
                    List<Product> products = service.findByCategory(category);
                    ProductUtils.printProducts(products);
                    break;
                case FIND_PRODUCT_NAME:
                    System.out.println("Input name: ");
                    String name = scanner.nextLine();
                    List<Product> products1 = service.findByName(name);
                    ProductUtils.printProducts(products1);
                    break;
                case FIND_PRODUCT_BRAND:
                    System.out.println("Input brand: ");
                    String brand = scanner.nextLine();
                    List<Product> products2 = service.findByBrand(brand);
                    ProductUtils.printProducts(products2);
                    break;
                case FIND_PRODUCT_PRICE:
                    System.out.println("Input min price: ");
                    double minPrice = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Input max price: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine();
                    List<Product> products3 = service.findByPriceRange(minPrice, maxPrice);
                    ProductUtils.printProducts(products3);
                    break;
                case EXIT:
                    System.out.println("Exit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
