package org.fatema.utils;

import org.fatema.model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductUtils {
    public static void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("The product list is empty");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    public static void printProduct(Product product) {
        if (product == null) {
            System.out.println("There are no products");
        } else {
            System.out.println(product);
        }
    }

    public static Product entryProductDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Id: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Input name: ");
        String name = scanner.nextLine();

        System.out.println("Input price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Input category:");
        String category = scanner.nextLine();

        System.out.println("Input brand:");
        String brand = scanner.nextLine();

        System.out.println("Input description:");
        String description = scanner.nextLine();

        return new Product(id, name, price, category, brand, description);
    }
}
