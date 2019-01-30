import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SolutionAlgorithm implements PuzzleSolver {
    private int[] terminate;
    private int[][] sublings;

    public SolutionAlgorithm(int[] terminate, int[][] sublings) {
        this.terminate = terminate;
        this.sublings = sublings;
    }

    public int[] resolve(int[] start) {
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

            for (State child : currentState.getChildren(sublings)) {
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
        for (State state : open) {
            if (state.getF() < minF) {
                minF = state.getF();
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
            res[i] = s;
            i++;
        }
        return res;
    }

}
