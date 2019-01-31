import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Puzzle {
    private static int[] terminate = new int[] {1, 2, 3, 4, 0, 5, 6, 7};
    private static int[][] siblings = new int[][] {
            {1, 2},
            {0, 2, 3},
            {0, 1, 5},
            {1, 4, 6},
            {3, 5},
            {2, 4, 7},
            {3, 7},
            {5, 6}
    };

    public static void main(String[] args) {
        int[] initialState = generateInitialState();
        System.out.println("Initial state: " + Arrays.toString(initialState));

        SolutionAlgorithm solution = new SolutionAlgorithm(terminate, siblings);

        int[] sequence = solution.resolve(initialState);

        String res = Arrays.stream(sequence)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
        System.out.println(res);

    }

    public static int[] generateInitialState() {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7};
//        int size = terminate.length;
//        ArrayList<Integer> list = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            list.add(i);
//        }
//
//        int[] state = new int[size];
//        Random r = new Random();
//
//        for (int i = 0; i < size; i++) {
//            state[i] = list.remove(r.nextInt(list.size()));
//        }
//        return state;
    }
}
