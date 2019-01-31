import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class State {
    private int g;
    private int h;
    private int movedValue;
    private State parent;
    private int[] state;
    private int hash;

    public State(int[] state) {
        this.state = state;
        this.hash = Arrays.hashCode(state);
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getMovedValue() {
        return movedValue;
    }

    public void setMovedValue(int movedValue) {
        this.movedValue = movedValue;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public int[] getState() {
        return state;
    }

    public List<State> getChildren(int[][] siblings) {
        int zeroIndex = IntStream.range(0, state.length)
                .filter(i -> 0 == state[i])
                .findFirst()
                .orElse(-1);

        List<State> children = new ArrayList<>();
        for (int siblingsIndex : siblings[zeroIndex]) {
            int[] childState = Arrays.copyOf(state, state.length);
            childState[zeroIndex] = state[siblingsIndex];
            childState[siblingsIndex] = 0;

            State child = new State(childState);
            child.setMovedValue(state[siblingsIndex]);
            child.setG(this.g + 1);

            children.add(child);
        }
        return children;
    }

    public int getF() {
        return g + h;
    }

    public boolean isTerminate(int[] terminate) {
        return Arrays.equals(state, terminate);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof State)) {
            return false;
        }

        return hash == obj.hashCode();
    }
}
