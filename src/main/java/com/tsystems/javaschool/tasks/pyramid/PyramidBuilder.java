package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        try {
            int[] input = new int[inputNumbers.size()];
            int count = inputNumbers.size();
            int height = 0;
            int width = 0;
            for (int i = 0; i < input.length; i++) {
                input[i] = inputNumbers.get(i);
            }
            Arrays.sort(input);
            for (int i = 1; i < input.length; i++) {
                if (count > 0) {
                    count = count - i;
                    width = (2 * i) - 1;
                    height++;
                }
            }
            if (count != 0) {
                throw new CannotBuildPyramidException();
            }
            int[][] output = new int[height][width];
            int a = 0;
            for (int y = 0; y < height; y++) {
                for (int x = (width / 2) - y; x < (width / 2) + y + 1; x = x + 2) {
                    output[y][x] = input[a++];
                }
            }
            return output;
        } catch (Error | Exception e) {
            throw new CannotBuildPyramidException();
        }
    }
}
