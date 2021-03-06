package by.it.obmetko.jd02_02;

import java.util.ArrayList;

class Dispatcher {

    static int speed = 25; //во сколько раз ускорить приложение
    private final static int planCount = 100; //сколько покупателей нужно всего

    private static int numberBuyer = 0; //счетчик покупателей
    private static int processCount = 0; //сколько их в магазине
    private static int factCount = 0;  //сколько вышло из магазина


    synchronized static boolean planComplete() {
        return factCount >= planCount;
    }


    synchronized static Buyer addNewBuyer() {
        processCount++;
        return new Buyer(++numberBuyer, Goods.goodsAccess);

    }

    private static ArrayList<Integer> chart = new ArrayList<>();
    synchronized static boolean needNext(int i){
        int t = i % 60;
        boolean todo = t < 30 ? processCount < (t + 10) : processCount <= 40 + (30 - t);
        if(!todo){
            Printer.printMessage("Покупателей в магазине: " + processCount);
            chart.add(processCount);
        }
        return todo;
    }


    synchronized static void finalBuyer() {
        processCount--;
        factCount++;
        if(processCount == 0){
            for (Cashier c: QueueBuyer.cashiers) {
                synchronized (c){
                    c.notify();
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Time\tAmount\n");
            int second = 0;
            for (Integer c: chart) {
                sb.append((second++) + "\t\t" + c + "\n");
            }
            Printer.printMessage(sb.toString());
        }
    }


    synchronized static boolean allBuyersInShop() {
        return numberBuyer == planCount;
    }


}