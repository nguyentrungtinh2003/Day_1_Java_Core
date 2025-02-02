import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ProductNotFoundException {
        ProductService productService = new ProductService();

        productService.addProduct(new Product(1L,"Laptop Dell",100));
        productService.addProduct(new Product(2L,"Laptop HP",200));
        productService.addProduct(new Product(3L,"Laptop Lenovo",150));

        Scanner scanner = new Scanner(System.in);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        while(true) {
            System.out.println("1. Add product");
            System.out.println("2. Get all product");
            System.out.println("3. Get by name product");
            System.out.println("4. Delete product");
            System.out.println("5. Total price products");

            System.out.println("Nhập lựa chọn của bạn :");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Nhap id san pham");
                        Long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Nhap ten san pham");
                        String name = scanner.nextLine();
                        System.out.println("Nhap gia san pham");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        productService.addProduct(new Product(id,name,price));
                        System.out.println("Add product success !");
                    }catch(Exception e) {
                        System.out.println("Error "+e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Danh sach cac san pham ");
                    sleep(3000);
                    executorService.execute(() -> {

                        productService.getAllProduct().forEach(System.out::println);
                    });
                    break;
                case 3:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String searchName = scanner.nextLine();
                    sleep(2000);
                    executorService.execute(() -> {

                        try {
                          Product product = productService.getProductByName(searchName);
                            System.out.println("Sản phẩm tìm thấy: " + product);
                        } catch (ProductNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                case 4:
                    try {
                        System.out.print("Nhập ID sản phẩm cần xóa: ");
                        Long idToRemove = scanner.nextLong();
                        scanner.nextLine(); // Clear buffer
                        productService.deleteProduct(idToRemove);
                        System.out.println("Xóa sản phẩm thành công!");
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Tổng giá trị sản phẩm: " + productService.sumPrice());
                    break;
                default:
                    System.out.println("khong hop le");
            }
        }


    }
    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
