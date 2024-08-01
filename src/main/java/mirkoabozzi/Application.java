package mirkoabozzi;

import jdk.jfr.Category;
import mirkoabozzi.entities.Customer;
import mirkoabozzi.entities.Order;
import mirkoabozzi.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {

        LocalDate today = LocalDate.now();

        Product product1 = new Product("iphone", "smartphone", 1000.00);
        Product product2 = new Product("libro1", "books", 120.00);
        Product product3 = new Product("libro2", "books", 110.00);
        Product product4 = new Product("pannolini", "baby", 5.00);
        Product product5 = new Product("latte", "baby", 2.00);
        Product product6 = new Product("deodorante", "boys", 5.00);
        Product product7 = new Product("dopobarba", "boys", 3.00);

        Customer cliente1 = new Customer("mirko", 1);
        Customer cliente2 = new Customer("skywalker", 2);
        Customer cliente3 = new Customer("darth vader", 2);

        List<Product> lista1 = new ArrayList<>();
        lista1.add(product1);
        lista1.add(product2);
        lista1.add(product3);
        lista1.add(product4);
        lista1.add(product5);
        lista1.add(product6);
        lista1.add(product7);

        List<Product> lista2 = new ArrayList<>();
        lista2.add(product2);
        lista2.add(product3);

        List<Product> lista3 = new ArrayList<>();
        lista3.add(product3);
        lista3.add(product4);

        List<Product> lista4 = new ArrayList<>();
        lista3.add(product4);
        lista3.add(product5);

        Order order1 = new Order("confermato", today, lista1, cliente1);
        Order order2 = new Order("confermato", today, lista2, cliente2);
        Order order3 = new Order("confermato", today.minusMonths(2), lista3, cliente2);
        Order order4 = new Order("confermato", today.minusMonths(1), lista4, cliente3);

        Predicate<Product> priceGreaterThanOneHundred = product -> product.getPrice() > 100;
        Predicate<Product> productCategoryBooks = product -> product.getCategory().equals("books");
        Predicate<Product> productCategoryBaby = product -> product.getCategory().equals("baby");
        Predicate<Product> productCategoryBoys = product -> product.getCategory().equals("boys");


        List<Product> listaEs1 = lista1.stream().filter(productCategoryBooks.and(priceGreaterThanOneHundred)).toList();
//        System.out.println(listaEs1);
        listaEs1.forEach(System.out::println);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);

        List<Order> orderListEs2 = orderList.stream().filter(order -> order.getProducts().stream().anyMatch(productCategoryBaby)).toList();

//        System.out.println(orderListEs2);
        orderListEs2.forEach(System.out::println);

        List<Product> listaEs3 = lista1.stream().filter(productCategoryBoys).toList();

        listaEs3.forEach(product -> {
            double newPrice = product.getPrice() * 0.90;
            product.setPrice(newPrice);
        });

//        System.out.println(listaEs3);
        listaEs3.forEach(System.out::println);

        List<Product> listaEs4 = orderList.stream().filter(order -> order.getCustomer().getTier() == 2 && order.getOrderDate().isBefore(LocalDate.parse("2024-07-31")) && order.getOrderDate().isAfter(LocalDate.parse("2024-05-01"))).flatMap(order -> order.getProducts().stream()).toList();
        listaEs4.forEach(System.out::println);

//        System.out.println(listaEs4);

        System.out.println("-----------------Lezione U4S2L4 Es.1---------------");

        Map<Customer, List<Order>> orderByClient = orderList.stream().collect(Collectors.groupingBy(order -> order.getCustomer()));

        orderByClient.forEach((customer, orders) -> System.out.println(customer + " - " + orders));

        System.out.println("-----------------Lezione U4S2L4 Es.2---------------");

//        Map<Customer,Double> venditePerCliente = orderList



        System.out.println("-----------------Lezione U4S2L4 Es.3---------------");

        List<Product> expansiveProduct =  lista1.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(5).toList();

        expansiveProduct.forEach(System.out::println);

        System.out.println("-----------------Lezione U4S2L4 Es.4---------------");


        Map<Order, Double> mediaImporti = orderList.stream().collect(Collectors.groupingBy(order -> order, Collectors.averagingDouble(order -> order.getProducts().stream().collect(Collectors.averagingDouble(Product::getPrice)))));

        mediaImporti.forEach((order, media) -> System.out.println("Order: " + order.getId() + ", average price: " + media));


        System.out.println("-----------------Lezione U4S2L4 Es.5---------------");

        Map<String,Double> sommaProdottiPerCategoria= lista1.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        sommaProdottiPerCategoria.forEach((category, totale) ->System.out.println(category + " - "+ totale));
    }
}