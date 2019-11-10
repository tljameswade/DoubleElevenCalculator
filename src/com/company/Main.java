package com.company;

import java.util.*;

/**
 * 小程序用来帮助计算在凑单的时候应该选哪些item比较合适。
 * 自己用的时候，可以点开一个java online compiler，比如：https://www.tutorialspoint.com/compile_java_online.php
 * 把本文件所有代码全部复制粘贴过去(ctrl + A 然后ctrl + c 接着ctrl + v）
 * 从99行开始是主程序。用户可以follow从104行开始的说明修改主程序中间的那一段代码换成你自己的use case，
 * 然后按下网页上的execute按钮，类似于下面的结果就会显示出来了：
 *
 * 芳香剂,海绵,车镜,桌布,指甲油,总和:128.21
 * 护膝,飞行枕,指甲油,总和:128.21
 * 芳香剂,发膜,车镜,总和:128.50
 * 芳香剂,车镜,飞行枕,收纳 X 2,总和:128.50
 * 海绵,桌布,指甲油,收纳 X 2,总和:129.61
 * 发膜,收纳 X 2,总和:129.90
 */
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
    private double range = 0;

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



        /**
         * To be replaced: 下面这一段可以换成你自己的use case。
         * 用的时候把第118行到131行换成自己的购物清单。
         * 比如添加"雅诗兰黛"，价格为101.3，你就可以自己加一行代码：
         * app.addItem("雅诗兰黛", 101.3);
         *
         * 然后需要set你需要凑得单的价格。比如你要凑600元的单，就可以加上
         * app.setTarget(600)；
         * 这一行代码来设置
         *
         * 如果你有一个范围可以允许浮动，如果你可以接受你的购物清单总价格在600元的基础上稍微超过个20元，那么你就可以加上：
         * app.setRange(20);
         * 来进行设置。
         */
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
