package interviews.snapsack;
import java.util.*;

public class SnapSackUnbounded {

    public static void main(String[] args) {
        int[][] items = {{1, 2}, {4, 3}, {5, 6}, {6, 7}}; //(value,weight)..
        var result = unboundedKnapsackProblem(items,10);
    }

    public static List<List<Integer>> unboundedKnapsackProblem(int[][] items, int capacityTotal) {

        /**
         * int[x][y]
         *
         * if (cap[j] >=item[i]) profits[i][j] =max( profits[i] + profits[i][j-item[i]], profits[i-1][j])
         */
        int[][] profits = new int[items.length+1][capacityTotal+1];
        for (int itemIdx=1; itemIdx<items.length+1; itemIdx++) {
            var item = itemIdx;
            var itemCapacity = items[itemIdx-1][0];
            var itemWeight = items[itemIdx-1][1];
            for(int capacity=1; capacity<=capacityTotal; capacity++) {
                if (capacity >= itemCapacity) {
                    profits[item][capacity] =
                            Math.max(itemWeight+profits[item][capacity-itemCapacity],
                                    profits[item-1][capacity]);
                }
            }
        }
        List<Integer> totalValue = Arrays.asList(profits[profits.length][profits[0].length]);
        List<Integer> finalItems = Arrays.asList(1, 2);
        var result = new ArrayList<List<Integer>>();
        result.add(totalValue);
        result.add(finalItems);
        return result;
    }
}

