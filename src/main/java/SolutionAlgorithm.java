import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SolutionAlgorithm implements PuzzleSolver {
    private int[] terminate;
    private int[][] siblings;

    public SolutionAlgorithm(int[] terminate, int[][] siblings) {
        this.terminate = terminate;
        this.siblings = siblings;
    }

    public int[] resolve(int[] start) {
        if (!isValidState(start)) {
            throw new IllegalArgumentException("Invalid state: " + Arrays.toString(start));
        }

        ArrayList<State> open = new ArrayList<>();
        ArrayList<Integer> close = new ArrayList<>();

        State initialState = new State(start);
        initialState.setG(0);
        initialState.setH(getH(start));
        open.add(initialState);

        while (!open.isEmpty()) {
            State currentState = getStateWithMinF(open);

            if (currentState.isTerminate(terminate)) {
                return getSequenceOfMoves(currentState);
            }
            open.remove(currentState);
            close.add(currentState.hashCode());

            for (State child : currentState.getChildren(siblings)) {
                if (close.contains(child.hashCode())) {
                    continue;
                }
                child.setH(getH(child.getState()));
                child.setParent(currentState);
                open.add(child);
            }
        }

        return new int[0];
    }

    // heuristic
    public int getH(int[] state) {
        int pos = 0;
        for (int i = 0; i < terminate.length; i++) {
            if (state[i] != terminate[i]) {
                pos++;
            }
        }
        return pos;
    }

    private State getStateWithMinF(ArrayList<State> open) {
        State res = null;
        int minF = Integer.MAX_VALUE;
        int maxG = 0;
        for (State state : open) {
            if (state.getF() == minF) {
                if (state.getG() > maxG) {
                    maxG = state.getG();
                    res = state;
                }
            }
            if (state.getF() < minF) {
                minF = state.getF();
                maxG = state.getG();
                res = state;
            }
        }
        return res;
    }

    private int[] getSequenceOfMoves(State terminate) {
        LinkedList<Integer> sequence = new LinkedList<>();
        State state = terminate;

        while (state != null) {
            if (state.getParent() != null) {
                int movedValue = state.getMovedValue();
                sequence.addFirst(movedValue);
            }
            state = state.getParent();
        }
        int[] res = new int[sequence.size()];

        int i = 0;
        for (int s : sequence) {
            res[i++] = s;
        }
        return res;
    }

    private boolean isValidState(int[] state) {
        int[] sortedTerminate = Arrays.copyOf(terminate, terminate.length);
        Arrays.sort(sortedTerminate);
        int terminateHash = Arrays.hashCode(sortedTerminate);

        int[] checkState = Arrays.copyOf(state, state.length);
        Arrays.sort(checkState);

        return terminateHash == Arrays.hashCode(checkState);
    }
}
