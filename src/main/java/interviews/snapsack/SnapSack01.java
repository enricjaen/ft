package interviews.snapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnapSack01 {

    public static void main(String[] args) {
        int[][] items = {{4, 3},{1, 2}, {5, 6}, {6, 7}}; //(profit,weight)..
        var result = knapsack01Problem(items,10);
    }

    public static List<List<Integer>> knapsack01Problem(int[][] items, int capacityTotal) {

        /**
         * int[x][y]
         *
         * if (cap[j] >=item[i]) profits[i][j] =max( profits[i] + profits[i][j-item[i]], profits[i-1][j])
         */
        int[][] profits = new int[items.length+1][capacityTotal+1];
        for (int itemIdx=1; itemIdx<items.length+1; itemIdx++) {
            var item = itemIdx;
            var itemProfit = items[itemIdx-1][0];
            var itemCapacity = items[itemIdx-1][1];
            for(int capacity=1; capacity<=capacityTotal; capacity++) {
                if (capacity >= itemCapacity) {
                    profits[item][capacity] =
                            Math.max(itemProfit+profits[item-1][capacity-itemCapacity],
                                    profits[item-1][capacity]);
                } else {
                    profits[item][capacity] = profits[item-1][capacity];
                }
                }
        }
        var totalValue = Arrays.asList(profits[profits.length-1][profits[0].length-1]);
        List<Integer> finalItems = findFinalItems(items,profits);
        var result = new ArrayList<List<Integer>>();
        result.add(totalValue);
        result.add(finalItems);
        return result;
    }

    private static List<Integer> findFinalItems(int[][] items, int[][] profits) {
        var finalItems = new ArrayList<Integer>();
        var itemIdx=profits.length-1;
        var capacity=profits[0].length-1;
        finalItems.add(itemIdx-1);
        capacity-=items[itemIdx-1][1];
        while (capacity > 0) {
            while (profits[itemIdx][capacity]==profits[itemIdx-1][capacity]) {
                itemIdx--;
            }
            finalItems.add(itemIdx-1);
            capacity-=items[itemIdx-1][1];
        }
        return finalItems;
    }
}

