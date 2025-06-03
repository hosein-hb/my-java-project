package ir.ac.kntu;

public class Filter {

    private boolean isFilter;
    private long maxPrice;
    private long minPrice;

    public Filter(boolean isFilter, long maxPrice, long minPrice) {
        this.isFilter = isFilter;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public boolean isFilter() {
        return isFilter;
    }

    public void setFilter(boolean isFilter) {
        this.isFilter = isFilter;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

}
