package ir.ac.kntu;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private final List<Seller> waitingList = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Support> supports = new ArrayList<>();
    private final List<Good> goods = new ArrayList<>();
    private final List<Request> allRequests = new ArrayList<>();
    private final Set<String> sellerCodes = new HashSet<>();
    private final Set<String> requestsCodes = new HashSet<>();
    private final Set<String> goodsCode = new HashSet<>();
    private final List<CustomerOrder> orders = new ArrayList<>();

    private static volatile Database instance;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Set<String> getGoodsCode() {
        return goodsCode;
    }

    public boolean addGoodCode(String code) {
        return goodsCode.add(code);
    }

    public List<Seller> getWaitingList() {
        return waitingList;
    }

    public void addToWaitingList(Seller seller) {
        waitingList.add(seller);
    }

    public boolean removeFromWaitingList(Seller seller) {
        return waitingList.remove(seller);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public List<Support> getSupports() {
        return new ArrayList<>(supports);
    }

    public void addSupport(Support support) {
        supports.add(support);
    }

    public boolean removeSupport(Support support) {
        return supports.remove(support);
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    public boolean removeGood(Good good) {
        return goods.remove(good);
    }

    public List<Request> getAllRequests() {
        return allRequests;
    }

    public void addRequest(Request request) {
        allRequests.add(request);
    }

    public boolean removeRequest(Request request) {
        return allRequests.remove(request);
    }

    public boolean addCode(String code) {
        return sellerCodes.add(code);
    }

    public Set<String> getSellerCodes() {
        return sellerCodes;
    }

    public Set<String> getRequestsCodes() {
        return requestsCodes;
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public boolean addOrder(CustomerOrder order) {
        return orders.add(order);
    }

}