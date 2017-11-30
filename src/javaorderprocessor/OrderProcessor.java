/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaorderprocessor;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Pattern;

public class OrderProcessor {
    
    static double tax = 0.02;
    static double shipping = 0.05;
    String orderLine;
    String orderID = "";
    String partNum = "";
    String completedOrder = "";
    double price = 0.0;
    int quantity = 0;
    ArrayList<String> orders = new ArrayList<>();
    
    public void OpenFile(String ordersFile, String processedOrdersFile) {
        
        /**
         * Creates a Buffered reader with the Orders.txt file and processes it 
         * into ArrayList "orders"
         */
        try(BufferedReader reader = new BufferedReader(new FileReader(ordersFile))) {
            orderLine = reader.readLine();
            for (int i = 0; !orderLine.isEmpty() && !orderLine.equals(null); i++) {
                orderLine = reader.readLine();
                if(!orderLine.equals("") && !orderLine.isEmpty() && !orderLine.equals(null)) {
                    orders.add(i, orderLine);
                }
            }
            reader.close();
            String orderSort[];
            ArrayList<String> completedOrderList = new ArrayList<>();
            
            System.out.println("Start processing orders.");
            
            /**
             * Splits each element of ArrayList "orders" into String array 
             * "orderSort," performs calculations on "orderSort" through method 
             * "OrderCalc" and adds the processed order into ArrayList 
             * "completedOrderList"
             */
            for (int i = 0; i < orders.size(); i++) {
                orderSort = orders.get(i).split(Pattern.quote("|"));
                completedOrderList.add(i, OrderCalc(orderSort[0], orderSort[1], 
                        Double.parseDouble(orderSort[2]), Integer.parseInt(orderSort[3])));
            }
            
            /**
             * Creates a Buffered writer and outputs the contents of 
             * "completedOrderList" into "OrdersProcessed.txt"
             */
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(processedOrdersFile))) {
                for (int i = 0; i < completedOrderList.size(); i++) {
                    writer.append(completedOrderList.get(i));
                    writer.newLine();
                }
                writer.close();
                
                System.out.println("Finished processing orders.");
            }
            catch(FileNotFoundException e) {
                System.out.println(e);
            }
            catch(IOException e) {
                System.out.println(e);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e);
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }    
    
    /**
     * Takes one individual order from "orderSort" at a time, calculates the 
     * order, and then returns the calculated order as a String
     */
    public String OrderCalc(String orderID, String partNum, double price, int quantity) {
        try {
            orderID += this.orderID;
            partNum += this.partNum;
            price += this.price;
            quantity += this.quantity;
            double itemTax = (price*quantity)*tax;
            double shippingCost = (price*quantity)*shipping;
            double total = (price*quantity)+itemTax+shippingCost;
            DecimalFormat priceFormat = new DecimalFormat("#0.00");
            DecimalFormat taxAndShippingFormat = new DecimalFormat("#0.00##");
            this.completedOrder = "Order ID: " + orderID + 
                            "\nPart Num: " + partNum + 
                            "\nPrice: " + priceFormat.format(price) + 
                            "\nQuantity: " + quantity + 
                            "\nTax: " + taxAndShippingFormat.format(itemTax) + 
                            "\nShipping: " + taxAndShippingFormat.format(shippingCost) + 
                            "\nTotal: " + taxAndShippingFormat.format(total) + 
                            "\n";
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return completedOrder;
    }
}
