import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private List<Product> products;
    public ProductService() {
        this.products = new ArrayList<>(); // Khởi tạo danh sách tránh NullPointerException
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getAllProduct() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    public Product getProductByName(String name) throws ProductNotFoundException {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
    }

    public void deleteProduct(Long id) throws ProductNotFoundException {
        boolean remove = products.removeIf(p -> p.getId() == id);
        if(!remove) {
            throw new ProductNotFoundException("product not found !");
        }
    }

    public double sumPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
