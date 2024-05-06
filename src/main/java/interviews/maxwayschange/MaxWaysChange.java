package interviews.maxwayschange;

import java.util.Arrays;
import java.util.List;

public class MaxWaysChange {
    public static void main(String[] args) {
        List<Integer> denoms = Arrays.asList(1,5,10,25);
        int targetAmount = 10;
        int result = maxWaysChange( denoms, targetAmount);
        System.out.println(result);
    }

    /**
     *
     ways [d+1][a+1]


     * @param denoms
     * @param targeAmount
     * @return
     */
    static int maxWaysChange(final List<Integer> denoms, final int finalTargetAmount) {
        int[][] ways = new int[denoms.size()+1][finalTargetAmount+1];
        ways[0][0] = 1;
        for (int i = 0; i < denoms.size(); i++) {  //for each denom
            for (int ta = 0; ta < finalTargetAmount+1; ta++) {
                if (ta >= denoms.get(i)) {
                    ways[i+1][ta] = ways[i][ta]+ ways[i+1][ta-denoms.get(i)];
                }
                else {
                    ways[i+1][ta] = ways[i][ta];
                }
            }
        }
        return ways[denoms.size()][finalTargetAmount];
    }
}
