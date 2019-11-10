package com.company;

import java.util.*;

public class Main {

    private class ShoppingCollection {
        double sum;
        List<String> shoppingChoice;

        ShoppingCollection(double sum, List<String> shoppingChoice) {
            this.sum = sum;
            this.shoppingChoice = new ArrayList<>(shoppingChoice);
        }
    }

    private class SortBySum implements Comparator<ShoppingCollection> {
        @Override
        public int compare(ShoppingCollection o1, ShoppingCollection o2) {
            if (o1.sum > o2.sum) {
                return 1;
            }
            if (o1.sum < o2.sum) {
                return -1;
            }
            return 0;
        }
    }

    private HashMap<String, Double> priceMap = new HashMap<>();
    private List<ShoppingCollection> shoppingChoices = new ArrayList<>();
    private double target;
    private double range;

    void setTarget(double target) {
        this.target = target;
    }

    void setRange(double range) {
        this.range = range;
    }

    List<String> getItemsList() {
        return new ArrayList<String>(this.priceMap.keySet());
    }

    double getTarget() {
        return this.target;
    }


    void addItem(String name, int price) {
        addItem(name, (double)price);
    }

    void addItem(String name, double price) {
        this.priceMap.put(name, price);
    }

    void calculate(List<String> itemsList, double remaining, int index, List<String> temp) {
        if (remaining + this.range < 0) {
            return;
        }
        if (remaining + this.range >= 0 && remaining <= 0) {
            this.shoppingChoices.add(new ShoppingCollection(target - remaining, temp));
        }
        for (int i = index; i < itemsList.size(); i++) {
            temp.add(itemsList.get(i));
            calculate(itemsList, remaining - priceMap.get(itemsList.get(i)),i + 1, temp);
            temp.remove(temp.size() - 1);
        }
    }

    void print() {
        Collections.sort(this.shoppingChoices, new SortBySum());
        for (ShoppingCollection shoppingCollection : shoppingChoices) {
            List<String> shoppingList = shoppingCollection.shoppingChoice;
            for (int i = 0; i < shoppingList.size(); i++) {
                System.out.print(shoppingList.get(i) + ",");
            }
            System.out.println(String.format("总和:%.2f", shoppingCollection.sum));
        }
    }

    public static void main(String[] args) {
        Main app = new Main();

        app.addItem("芳香剂", 39.9);
        app.addItem("飞行枕", 29.9);
        app.addItem("收纳 X 2", 50.0);
        app.addItem("护膝", 84.0);
        app.addItem("桌布", 17.5);
        app.addItem("指甲油", 14.31);
        app.addItem("按摩椅", 167);
        app.addItem("海绵", 47.8);
        app.addItem("墨镜", 260);
        app.addItem("洁牙器", 232);
        app.addItem("发膜", 79.9);
        app.addItem("车镜", 8.7);

        app.setTarget(127.9);
        app.setRange(10);

        app.calculate(app.getItemsList(), app.getTarget(), 0, new ArrayList<String>());
        app.print();
    }
}
