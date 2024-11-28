package com.laptop.service;

import com.laptop.models.Product;
import com.laptop.dao.ProductDAO;
import com.laptop.utils.Protector;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService extends ProductDAO {
    public ProductService() {
    }

    public int count() {
        try (Session session = getCurrentSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(id) FROM Product", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }

    public String getFirst(String twopartString) {
        return twopartString.contains("-") ? twopartString.split("-")[0] : "";
    }

    public String getLast(String twopartString) {
        return twopartString.contains("-") ? twopartString.split("-")[1] : "";
    }

    private int getMinPrice(String priceRange) {
        return Protector.of(() -> Integer.parseInt(getFirst(priceRange))).get(0);
    }

    private int getMaxPrice(String priceRange) {
        return Protector.of(() -> {
            String maxPriceString = getLast(priceRange);
            if (maxPriceString.equals("infinity")) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(maxPriceString);
        }).get(0);
    }

    public String filterByPublishers(List<String> publishers) {
        String publishersString = publishers.stream().map(p -> "'" + p + "'").collect(Collectors.joining(", "));
        return "p.publisher IN (" + publishersString + ")";
    }

    public String filterByPriceRanges(List<String> priceRanges) {
        // Initialize a StringBuilder to build the filter string
        StringBuilder filterString = new StringBuilder();

        // Loop through each price range and construct the filter condition
        for (int i = 0; i < priceRanges.size(); i++) {
            String range = priceRanges.get(i);
            String[] prices = range.split("-");  // Split the range into minPrice and maxPrice

            if (prices.length == 2) {
                try {
                    // Parse min and max prices
                    double minPrice = Double.parseDouble(prices[0]);
                    double maxPrice = Double.parseDouble(prices[1]);

                    // Add the price range filter condition
                    if (i > 0) {
                        filterString.append(" OR ");  // Use OR between multiple price ranges
                    }
                    filterString.append(String.format("p.price >= %.2f AND p.price <= %.2f", minPrice, maxPrice));

                } catch (NumberFormatException e) {
                    // Handle invalid number format, log it or throw an exception
                    throw new IllegalArgumentException("Invalid price range format: " + range);
                }
            }
        }

        // Return the constructed filter string
        return filterString.toString();
    }

    public String createFiltersQuery(List<String> filters) {
        return String.join(" AND ", filters);
    }

    public List<String> getRamsByCategoryId(long categoryId) {
        return getProductDAO().getDistinctRAMValues();
    }

    public List<String> getCpusByCategoryId(long categoryId) {
        return getProductDAO().getDistinctCPUsValues();
    }

    public List<String> getVgasByCategoryId(long categoryId) {
        return getProductDAO().getDistinctVGAValues();
    }

    public String filterByRams(List<String> rams) {
        String ramsString = rams.stream().map(r -> "'" + r + "'").collect(Collectors.joining(", "));
        return "p.RAM IN (" + ramsString + ")";
    }

    public String filterByCpus(List<String> cpus) {
        String cpusString = cpus.stream().map(c -> "'" + c + "'").collect(Collectors.joining(", "));
        return "p.CPU IN (" + cpusString + ")";
    }

    public String filterByVgas(List<String> vgas) {
        String vgasString = vgas.stream().map(v -> "'" + v + "'").collect(Collectors.joining(", "));
        return "p.VGA IN (" + vgasString + ")";
    }

    private ProductDAO getProductDAO() {
        return new ProductDAO();
    }



}
